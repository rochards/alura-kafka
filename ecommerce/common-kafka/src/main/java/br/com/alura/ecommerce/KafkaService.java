package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.Closeable;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

public class KafkaService<T> implements Closeable {

    private final KafkaConsumer<String, T> consumer;
    private final ConsumerFunction<T> parse;

    private KafkaService(String groupIdName, ConsumerFunction<T> parse, Class<T> type, Map<String,
            String> properties) {
        this.consumer = new KafkaConsumer<>(getProperties(type, groupIdName, properties));
        this.parse = parse;
    }

    public KafkaService(String groupIdName, String topic, ConsumerFunction<T> parse, Class<T> type, Map<String,
            String> properties) {
        this(groupIdName, parse, type, properties);
        consumer.subscribe(List.of(topic));
    }

    public KafkaService(String groupIdName, Pattern topics, ConsumerFunction<T> parse, Class<T> type, Map<String,
            String> properties) {
        this(groupIdName, parse, type, properties);
        consumer.subscribe(topics);
    }

    public void run() {
        while (true) {
            var records = consumer.poll(Duration.ofMillis(100));
            if (!records.isEmpty()) {
                System.out.println("Encontrados " + records.count() + " registro(s)");
                for (var record : records) {
                    parse.consume(record);
                }
            }
        }
    }

    private Properties getProperties(Class<T> type, String groupIdName, Map<String, String> overrideProperties) {
        var properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());

        /* um consumer precisa de um grupo configurado.
        Se tiver mais de um serviço em um mesmo grupo fica difícil saber qual serviço recebeu qual mensagem, mas no
        final todas serão processadas. */
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupIdName);

        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
        properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
        properties.putAll(overrideProperties);

        return properties;
    }

    @Override
    public void close() {
        consumer.close();
    }
}

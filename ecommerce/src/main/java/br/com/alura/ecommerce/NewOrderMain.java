package br.com.alura.ecommerce;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // esses properties poderia ser na verdade um arquivo
        var producer = new KafkaProducer<String, String>(properties());
        Callback listener = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso " + data.topic() + "::partition " + data.partition() + "/ offset " + data.offset() + "/ timestamp " + data.timestamp());
        };

        var value = "1234134, 1245, 1234.00";
        var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", "Order", value);
        producer.send(record, listener) // send é assíncrono, por isso damos um get
                .get();

        var email = "We are processing your order!";
        var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL", "Email", email);
        producer.send(emailRecord, listener).get();
    }

    private static Properties properties() {
        var properties = new Properties();

        // onde está o Kafka?
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // de que tipo é a chave? -> configuração para serializar de string para byte
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // de que tipo é a mensagem? -> configuração para serializar de string para byte
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }
}

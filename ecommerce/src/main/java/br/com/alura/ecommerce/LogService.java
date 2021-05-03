package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.regex.Pattern;

public class LogService {
    public static void main(String[] args) {

        try (var logService = new KafkaService(LogService.class.getSimpleName(), Pattern.compile("ECOMMERCE.*"), LogService::parse)) {
            logService.run();
        }
    }

    private static void parse(ConsumerRecord<String, String> record) {
        System.out.println("---------------");
        System.out.println("LOG: " + record.topic());
        System.out.println("key: " + record.key());
        System.out.println("message/value: " + record.value());
        System.out.println("partition: " + record.partition());
        System.out.println("offset: " + record.offset());
    }
}

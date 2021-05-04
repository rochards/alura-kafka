package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.Map;

public class EmailService {
    public static void main(String[] args) {
        try (var service = new KafkaService<>(EmailService.class.getSimpleName(),"ECOMMERCE_SEND_EMAIL",
                EmailService::parse, Email.class, Map.of())){
            service.run();
        }
    }

    private static void parse(ConsumerRecord<String, Email> record) {
        System.out.println("---------------");
        System.out.println("Send email");
        System.out.println("key: " + record.key());
        System.out.println("message/value: " + record.value());
        System.out.println("partition: " + record.partition());
        System.out.println("offset: " + record.offset());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Email sent");
    }
}

package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class FraudDetectorService {
    public static void main(String[] args) {
        try (var service = new KafkaService(FraudDetectorService.class.getSimpleName(),"ECOMMERCE_NEW_ORDER",
                FraudDetectorService::parse)) {
            service.run();
        }
    }

    private static void parse(ConsumerRecord<String, String> record) {
        System.out.println("---------------");
        System.out.println("Processing new order, checking for fraud");
        System.out.println("key: " + record.key());
        System.out.println("message/value: " + record.value());
        System.out.println("partition: " + record.partition());
        System.out.println("offset: " + record.offset());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Order processed");
    }
}

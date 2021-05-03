package br.com.alura.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class NewOrderMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        var orderDispatcher = new KafkaDispatcher<Order>();
        var emailDispatcher = new KafkaDispatcher<String>();

        for (int i = 0; i < 10; i++) {

            var userId = UUID.randomUUID().toString();
            var orderId = UUID.randomUUID().toString();
            var amount = BigDecimal.valueOf(Math.random() * 5000 + 10);
            var order = new Order(userId, orderId, amount);
            orderDispatcher.send("ECOMMERCE_NEW_ORDER", "order" + orderId, order);

            var emailKey = UUID.randomUUID().toString();
            var email = "We are processing your order!";
            emailDispatcher.send("ECOMMERCE_SEND_EMAIL", "email" + emailKey, email);
        }
    }
}

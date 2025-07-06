package com.store.product.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.store.kafka.ProductNotification;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductProducer {

    private final KafkaTemplate<String, ProductNotification> kafkaTemplate;

    public void sendProductNotification(ProductNotification productNotification) {
        log.info("Sending Product notification to Manufacturer: {}", productNotification);
        Message<ProductNotification> message = MessageBuilder
                .withPayload(productNotification)
                .setHeader(TOPIC, "product-topic")
                .build();

        kafkaTemplate.send(message);
        log.info("Product notification sent successfully");
    }
}

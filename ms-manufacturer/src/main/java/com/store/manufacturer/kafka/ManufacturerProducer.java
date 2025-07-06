package com.store.manufacturer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.store.kafka.ManufacturerNotification;

import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManufacturerProducer {

    private final KafkaTemplate<String, ManufacturerNotification> kafkaTemplate;

    public void sendManufacturerNotification(ManufacturerNotification manufacturerNotification) {
        log.info("Sending manufacturer notification");
        Message<ManufacturerNotification> message = MessageBuilder
                .withPayload(manufacturerNotification)
                .setHeader(TOPIC, "manufacturer-topic")
                .build();

        kafkaTemplate.send(message);
        log.info("Manufacturer notification sent successfully");
    }
}

package com.store.notification.kafka;

import com.store.kafka.ManufacturerNotification;
import com.store.kafka.ProductNotification;
import com.store.notification.email.EmailService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import static java.lang.String.format;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationsConsumer {

        private final EmailService emailService;

        @KafkaListener(topics = "manufacturer-topic")
        public void consumeManufacturerNotifications(ManufacturerNotification manufacturerNotification)
                        throws MessagingException {
                log.info(format("Consuming the message from manufacturer-topic Topic:: %s", manufacturerNotification));
                emailService.sendManufacturerNotificationEmail(
                                manufacturerNotification.email(),
                                manufacturerNotification.name(),
                                manufacturerNotification.founder(),
                                manufacturerNotification.country());
                log.info("INFO - Manufacturer notification email sent successfully");
        }

        @KafkaListener(topics = "product-topic")
        public void consumeProductNotifications(ProductNotification productNotification)
                        throws MessagingException {
                log.info(format("Consuming the message from product-topic Topic:: %s", productNotification));
                emailService.sendProductNotificationEmail(
                                productNotification.email(),
                                productNotification.name(),
                                productNotification.manufacturerId(),
                                productNotification.price(),
                                productNotification.category(),
                                productNotification.inStock());
                log.info("INFO - Product notification email sent successfully");
        }

}

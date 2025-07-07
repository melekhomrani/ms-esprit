package com.store.notification.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static com.store.notification.email.EmailTemplates.NEW_MANUFACTURER_ADDED;
import static com.store.notification.email.EmailTemplates.NEW_PRODUCT_ADDED;
import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    
    @Async
    public void sendManufacturerNotificationEmail(
            String destinationEmail,
            String name,
            String founder,
            String country
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("contact@store.com");

        final String templateName = NEW_MANUFACTURER_ADDED.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("name", name);
        variables.put("founder", founder);
        variables.put("country", country);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(NEW_MANUFACTURER_ADDED.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
        }

    }

    @Async
    public void sendProductNotificationEmail(
            String destinationEmail,
            String name,
            String manufacturerId,
            Double price,
            String category,
            boolean inStock
    ) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, UTF_8.name());
        messageHelper.setFrom("contact@store.com");

        final String templateName = NEW_PRODUCT_ADDED.getTemplate();

        Map<String, Object> variables = new HashMap<>();
        variables.put("name", name);
        variables.put("manufacturer", manufacturerId);
        variables.put("price", price);
        variables.put("category", category);
        variables.put("inStock", inStock);

        Context context = new Context();
        context.setVariables(variables);
        messageHelper.setSubject(NEW_PRODUCT_ADDED.getSubject());

        try {
            String htmlTemplate = templateEngine.process(templateName, context);
            messageHelper.setText(htmlTemplate, true);

            messageHelper.setTo(destinationEmail);
            mailSender.send(mimeMessage);
            log.info(String.format("INFO - Email successfully sent to %s with template %s ", destinationEmail, templateName));
        } catch (MessagingException e) {
            log.warn("WARNING - Cannot send Email to {} ", destinationEmail);
        }

    }

}

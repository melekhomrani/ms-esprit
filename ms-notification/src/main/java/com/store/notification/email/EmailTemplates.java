package com.store.notification.email;

import lombok.Getter;

public enum EmailTemplates {

    NEW_MANUFACTURER_ADDED("manufacturer-notification.html", "New manufacturer added"),
    NEW_PRODUCT_ADDED("product-notification.html", "New product added")
    ;

    @Getter
    private final String template;
    @Getter
    private final String subject;


    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }
}

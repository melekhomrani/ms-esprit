package com.store.kafka;

public record ProductNotification(
    String email,
    String name,
    String manufacturerId,
    Double price,
    String category,
    boolean inStock) {

}

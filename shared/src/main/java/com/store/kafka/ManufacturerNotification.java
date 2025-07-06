package com.store.kafka;

public record ManufacturerNotification(
    String email,
    String name,
    String founder,
    String country) {
}
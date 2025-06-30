package com.store.manufacturer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient 
@SpringBootApplication
public class ManufacturerServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManufacturerServiceApplication.class, args);
    }
}
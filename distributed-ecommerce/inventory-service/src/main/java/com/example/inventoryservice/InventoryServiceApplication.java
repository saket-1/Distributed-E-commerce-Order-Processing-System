package com.example.inventoryservice;

import com.example.inventoryservice.entity.InventoryItem;
import com.example.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    // Bean to add some initial inventory data for testing
    @Bean
    CommandLineRunner loadData(InventoryRepository repository) {
        return args -> {
            if (repository.findByProductId("PRODUCT_123").isEmpty()) {
                repository.save(new InventoryItem("PRODUCT_123", 100));
            }
            if (repository.findByProductId("PRODUCT_456").isEmpty()) {
                repository.save(new InventoryItem("PRODUCT_456", 50));
            }
        };
    }
}
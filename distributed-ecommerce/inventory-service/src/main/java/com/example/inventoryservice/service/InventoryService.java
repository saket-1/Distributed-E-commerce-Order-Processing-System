package com.example.inventoryservice.service;

import com.example.common.events.OrderCreatedEvent;
import com.example.inventoryservice.entity.InventoryItem;
import com.example.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @KafkaListener(topics = "${kafka.topic.order-created}", groupId = "${spring.kafka.consumer.group-id}")
    @Transactional // Ensure DB update is atomic
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Received OrderCreatedEvent for orderId: {}, productId: {}, quantity: {}",
                event.getOrderId(), event.getProductId(), event.getQuantity());

        Optional<InventoryItem> itemOptional = inventoryRepository.findByProductId(event.getProductId());

        if (itemOptional.isPresent()) {
            InventoryItem item = itemOptional.get();
            if (item.getQuantity() >= event.getQuantity()) {
                item.setQuantity(item.getQuantity() - event.getQuantity());
                inventoryRepository.save(item);
                log.info("Inventory updated for productId: {}. New quantity: {}", item.getProductId(), item.getQuantity());
                // Optionally publish InventoryUpdatedEvent
            } else {
                log.warn("Insufficient inventory for productId: {}. Required: {}, Available: {}",
                        item.getProductId(), event.getQuantity(), item.getQuantity());
                // Handle insufficient stock - e.g., publish OrderFailedEvent
            }
        } else {
            log.warn("Inventory item not found for productId: {}", event.getProductId());
            // Handle missing product - e.g., publish OrderFailedEvent
        }
    }
}
package com.example.notificationservice.service;

import com.example.common.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationListener {

    @KafkaListener(topics = "${kafka.topic.order-created}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        // Simulate sending a notification (e.g., email, SMS)
        log.info("Received OrderCreatedEvent - Sending notification for orderId: {}", event.getOrderId());
        // Add actual notification logic here if needed (e.g., call email service)
        log.info("Simulated notification sent for orderId: {}", event.getOrderId());
    }
}
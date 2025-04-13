package com.example.orderservice.service;

import com.example.common.events.OrderCreatedEvent;
import com.example.orderservice.entity.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Value("${kafka.topic.order-created}")
    private String orderCreatedTopic;

    @Transactional // Ensure save and send are atomic if possible (best effort here)
    public Order createOrder(String productId, int quantity) {
        log.info("Creating order for productId: {}, quantity: {}", productId, quantity);
        Order order = new Order(productId, quantity);
        order = orderRepository.save(order);
        log.info("Order saved with ID: {}", order.getId());

        OrderCreatedEvent event = new OrderCreatedEvent(order.getId(), order.getProductId(), order.getQuantity());

        try {
            kafkaTemplate.send(orderCreatedTopic, String.valueOf(order.getId()), event);
            log.info("Published OrderCreatedEvent for orderId: {}", order.getId());
        } catch (Exception e) {
            // Handle Kafka publish failure - potentially mark order as failed or schedule retry
            log.error("Failed to publish OrderCreatedEvent for orderId: {}. Error: {}", order.getId(), e.getMessage());
            order.setStatus("PUBLISH_FAILED");
            orderRepository.save(order); // Update status
            // Depending on requirements, might throw exception to rollback DB transaction
        }
        return order;
    }
}
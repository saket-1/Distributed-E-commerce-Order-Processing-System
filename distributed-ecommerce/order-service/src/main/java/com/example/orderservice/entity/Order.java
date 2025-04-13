package com.example.orderservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CUSTOMER_ORDER") // ORDER is often a reserved keyword
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productId;
    private int quantity;
    private String status; // e.g., PENDING, CREATED, FAILED

    public Order(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
        this.status = "CREATED";
    }
}
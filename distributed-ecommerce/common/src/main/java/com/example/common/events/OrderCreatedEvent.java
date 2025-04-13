package com.example.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreatedEvent implements Serializable {
    private static final long serialVersionUID = 1L; // Good practice for Serializable
    private Long orderId;
    private String productId;
    private int quantity;
}
package com.example.inventoryservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INVENTORY_ITEM")
@Data
@NoArgsConstructor
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String productId;
    private int quantity;

    public InventoryItem(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}
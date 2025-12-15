package com.manas.smart_kitchen_assistant.model;

import jakarta.persistence.Entity;     // For @Entity, @Id, etc.
import jakarta.persistence.GeneratedValue;                 // For @Getter, @Setter, etc.
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double quantity;
    private String unit; // e.g., "grams", "liters", "pieces"
    private String category; // e.g., "vegetables", "fruits", "dairy"

}
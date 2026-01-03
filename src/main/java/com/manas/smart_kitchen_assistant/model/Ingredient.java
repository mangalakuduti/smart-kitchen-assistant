package com.manas.smart_kitchen_assistant.model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingredient {

    @Id
    private String id;

    private String name;
    private double quantity;
    private String unit; // e.g., "grams", "liters", "pieces"
    private String category; // e.g., "vegetables", "fruits", "dairy"

}
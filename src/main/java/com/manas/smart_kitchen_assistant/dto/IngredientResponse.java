package com.manas.smart_kitchen_assistant.dto;

import lombok.Data;

@Data
public class IngredientResponse {
    private String id;
    private String name;
    private double quantity;
    private String unit;
    private String category;
}
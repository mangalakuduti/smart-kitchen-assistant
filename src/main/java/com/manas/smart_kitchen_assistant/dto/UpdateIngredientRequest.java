package com.manas.smart_kitchen_assistant.dto;

import lombok.Data;

@Data
public class UpdateIngredientRequest {
    private String name;
    private Double quantity;
    private String unit;
    private String category;
}

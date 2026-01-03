package com.manas.smart_kitchen_assistant.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CreateIngredientRequest {
    private String name;
    private double quantity;
    private String unit;
    private String category;
}
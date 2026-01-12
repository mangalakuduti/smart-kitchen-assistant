package com.manas.smart_kitchen_assistant.dto;

import java.util.List;

import com.manas.smart_kitchen_assistant.model.Ingredient;

import lombok.Data;

@Data
public class CreateRecipeRequest {
    private String name;
    private Integer serves;
    private List<Ingredient> ingredients;
    private Integer cookingTime;
    private String instructions;
}
package com.manas.smart_kitchen_assistant.dto;

import java.util.List;

import com.manas.smart_kitchen_assistant.model.Ingredient;

import lombok.Data;

@Data
public class RecipeResponse {
    private String id;
    private String name;
    private int serves;
    private List<Ingredient> ingredients;
    private int cookingTime;
    private String instructions;
    private boolean hasIngredients;
}
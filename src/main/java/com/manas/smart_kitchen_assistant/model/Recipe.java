package com.manas.smart_kitchen_assistant.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "recipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Recipe {

    @Id
    private String id;

    private String name;
    private int serves;
    private List<Ingredient> ingredients;
    private int cookingTime;
    private String instructions;
    private boolean hasIngredients;
    
}

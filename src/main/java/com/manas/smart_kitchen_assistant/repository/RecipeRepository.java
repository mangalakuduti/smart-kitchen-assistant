package com.manas.smart_kitchen_assistant.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.manas.smart_kitchen_assistant.model.Recipe;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
}
package com.manas.smart_kitchen_assistant.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.manas.smart_kitchen_assistant.model.Ingredient;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {

	Optional<Ingredient> findByNameIgnoreCase(String name);

}
package com.manas.smart_kitchen_assistant.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.manas.smart_kitchen_assistant.dto.CreateRecipeRequest;
import com.manas.smart_kitchen_assistant.dto.RecipeResponse;
import com.manas.smart_kitchen_assistant.dto.UpdateRecipeRequest;
import com.manas.smart_kitchen_assistant.model.Ingredient;
import com.manas.smart_kitchen_assistant.model.Recipe;
import com.manas.smart_kitchen_assistant.repository.IngredientRepository;
import com.manas.smart_kitchen_assistant.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository repository;
    private final IngredientRepository ingredientRepository;

    public RecipeResponse createRecipe(CreateRecipeRequest req) {
        Recipe recipe = Recipe.builder()
            .name(req.getName())
            .serves(orZero(req.getServes()))
            .ingredients(req.getIngredients())
            .cookingTime(orZero(req.getCookingTime()))
            .instructions(req.getInstructions())
            .hasIngredients(false) // Will be calculated before save
                .build();

        recipe.setHasIngredients(checkInventoryAvailability(recipe.getIngredients()));
        Recipe saved = repository.save(recipe);
        return mapToResponse(saved);
    }

    public List<RecipeResponse> getAllRecipes() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public RecipeResponse getRecipeById(String id) {
        Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recipe not found with id: " + id));
        return mapToResponse(recipe);
    }

    public void deleteRecipeById(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found with id: " + id);
        }
        repository.deleteById(id);
    }

    public RecipeResponse updateRecipe(String id, UpdateRecipeRequest req) {
        Recipe recipe = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Recipe not found with id: " + id));

        if (req.getName() != null) {
            recipe.setName(req.getName());
        }
        if (req.getServes() != null) {
            recipe.setServes(req.getServes());
        }
        if (req.getIngredients() != null) {
            recipe.setIngredients(req.getIngredients());
            // Recalculate availability when ingredients change
            recipe.setHasIngredients(checkInventoryAvailability(recipe.getIngredients()));
        }
        if (req.getCookingTime() != null) {
            recipe.setCookingTime(req.getCookingTime());
        }
        if (req.getInstructions() != null) {
            recipe.setInstructions(req.getInstructions());
        }

        Recipe updated = repository.save(recipe);
        return mapToResponse(updated);
    }

    private int orZero(Integer value) {
        return value != null ? value : 0;
    }

    private RecipeResponse mapToResponse(Recipe recipe) {
        RecipeResponse res = new RecipeResponse();
        res.setId(recipe.getId());
        res.setName(recipe.getName());
        res.setServes(recipe.getServes());
        res.setIngredients(recipe.getIngredients());
        res.setCookingTime(recipe.getCookingTime());
        res.setInstructions(recipe.getInstructions());
        res.setHasIngredients(recipe.isHasIngredients());
        return res;
    }

    private boolean checkInventoryAvailability(List<Ingredient> requiredIngredients) {
        if (requiredIngredients == null || requiredIngredients.isEmpty()) {
            return true; // No ingredients needed
        }

        for (Ingredient required : requiredIngredients) {
            var inStock = ingredientRepository.findByNameIgnoreCase(required.getName());
            
            if (inStock.isEmpty()) {
                return false; // Ingredient not found in inventory
            }
            
            if (inStock.get().getQuantity() < required.getQuantity()) {
                return false; // Insufficient quantity
            }
        }
        
        return true; // All ingredients available in sufficient quantities
    }

    /**
     * Recalculate hasIngredients for all recipes
     * @return number of recipes updated
     */
    public int recalculateAllRecipes() {
        List<Recipe> allRecipes = repository.findAll();
        int updatedCount = 0;
        
        for (Recipe recipe : allRecipes) {
            boolean hasIngredients = checkInventoryAvailability(recipe.getIngredients());
            if (recipe.isHasIngredients() != hasIngredients) {
                recipe.setHasIngredients(hasIngredients);
                repository.save(recipe);
                updatedCount++;
            }
        }
        
        return updatedCount;
    }

    /**
     * Recalculate hasIngredients for recipes containing a specific ingredient
     * @param ingredientName the ingredient name (case-insensitive)
     * @return number of recipes updated
     */
    public int recalculateRecipesWithIngredient(String ingredientName) {
        List<Recipe> allRecipes = repository.findAll();
        int updatedCount = 0;
        
        for (Recipe recipe : allRecipes) {
            // Check if recipe contains this ingredient
            boolean containsIngredient = recipe.getIngredients() != null &&
                recipe.getIngredients().stream()
                    .anyMatch(ing -> ing.getName().equalsIgnoreCase(ingredientName));
            
            if (containsIngredient) {
                boolean hasIngredients = checkInventoryAvailability(recipe.getIngredients());
                if (recipe.isHasIngredients() != hasIngredients) {
                    recipe.setHasIngredients(hasIngredients);
                    repository.save(recipe);
                    updatedCount++;
                }
            }
        }
        
        return updatedCount;
    }
}
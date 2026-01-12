package com.manas.smart_kitchen_assistant.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manas.smart_kitchen_assistant.dto.CreateRecipeRequest;
import com.manas.smart_kitchen_assistant.dto.RecipeResponse;
import com.manas.smart_kitchen_assistant.dto.UpdateRecipeRequest;
import com.manas.smart_kitchen_assistant.service.RecipeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
@CrossOrigin
public class RecipeController {

    private final RecipeService service;

    @PostMapping
    public RecipeResponse createRecipe(@RequestBody CreateRecipeRequest req) {
        return service.createRecipe(req);
    }

    @GetMapping
    public List<RecipeResponse> getAllRecipes() {
        return service.getAllRecipes();
    }

    @GetMapping("/{id}")
    public RecipeResponse getRecipeById(@PathVariable String id) {
        return service.getRecipeById(id);
    }

    @PatchMapping("/{id}")
    public RecipeResponse updateRecipe(
            @PathVariable String id,
            @RequestBody UpdateRecipeRequest req) {
        return service.updateRecipe(id, req);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable String id) {
        service.deleteRecipeById(id);
    }

    @PostMapping("/refresh-availability")
    public String refreshAvailability() {
        int updatedCount = service.recalculateAllRecipes();
        return "Updated " + updatedCount + " recipe(s)";
    }
}
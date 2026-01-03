package com.manas.smart_kitchen_assistant.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manas.smart_kitchen_assistant.dto.CreateIngredientRequest;
import com.manas.smart_kitchen_assistant.dto.IngredientResponse;
import com.manas.smart_kitchen_assistant.dto.UpdateIngredientRequest;
import com.manas.smart_kitchen_assistant.service.IngredientService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
@CrossOrigin // allow React frontend
public class IngredientController {

    private final IngredientService service;

    @PostMapping
    public IngredientResponse createItem(@RequestBody CreateIngredientRequest req) {
        return service.createItem(req);
    }

    @GetMapping
    public Object getItems(@RequestParam(name = "name", required = false) String name) {
        if (name != null && !name.isBlank()) {
            return service.getItemByName(name.trim());
        }
        return service.getAllItems();
    }

    @GetMapping("/{id}")
    public IngredientResponse getItemById(
        @PathVariable String id
    ) {
        return service.getItemById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteItemById(@PathVariable String id) {
        service.deleteItemById(id);
    }

    @PatchMapping("/{id}")
    public IngredientResponse updateItem(
            @PathVariable String id,
            @RequestBody UpdateIngredientRequest req) {
        return service.updateItem(id, req);
    }

    
}
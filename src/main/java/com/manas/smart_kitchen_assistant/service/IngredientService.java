package com.manas.smart_kitchen_assistant.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.manas.smart_kitchen_assistant.dto.CreateIngredientRequest;
import com.manas.smart_kitchen_assistant.dto.IngredientResponse;
import com.manas.smart_kitchen_assistant.dto.UpdateIngredientRequest;
import com.manas.smart_kitchen_assistant.model.Ingredient;
import com.manas.smart_kitchen_assistant.repository.IngredientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository repository;

    public IngredientResponse createItem(CreateIngredientRequest req) {

        Ingredient item = Ingredient.builder()
                .name(req.getName())
                .quantity(req.getQuantity())
                .unit(req.getUnit())
                .category(req.getCategory())
                .build();

        Ingredient saved = repository.save(item);

        IngredientResponse res = new IngredientResponse();
        res.setId(saved.getId());
        res.setName(saved.getName());
        res.setQuantity(saved.getQuantity());
        res.setUnit(saved.getUnit());
        res.setCategory(saved.getCategory());

        return res;
    }

    public List<IngredientResponse> getAllItems() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public IngredientResponse getItemById(String id) {
        Ingredient item = repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ingredient not found with id: " + id
                )
            );
        return mapToResponse(item);
    }

    public IngredientResponse getItemByName(String name) {
        Ingredient item = repository.findByNameIgnoreCase(name)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ingredient not found with name: " + name
                )
            );
        return mapToResponse(item);
    }

    public void deleteItemById(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Ingredient not found with id: " + id
            );
        }
        repository.deleteById(id);
    }

    public IngredientResponse updateItem(String id, UpdateIngredientRequest req) {
        Ingredient item = repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ingredient not found with id: " + id
                )
            );
        if (req.getName() != null) {
            item.setName(req.getName());
        }
        if (req.getQuantity() != null) {
            item.setQuantity(req.getQuantity());
        }
        if (req.getUnit() != null) {
            item.setUnit(req.getUnit());
        }
        if (req.getCategory() != null) {
            item.setCategory(req.getCategory());
        }

        Ingredient updated = repository.save(item);
        return mapToResponse(updated);
    }

    private IngredientResponse mapToResponse(Ingredient item) {
        IngredientResponse res = new IngredientResponse();
        res.setId(item.getId());
        res.setName(item.getName());
        res.setQuantity(item.getQuantity());
        res.setUnit(item.getUnit());
        res.setCategory(item.getCategory());
        return res;
    }
}
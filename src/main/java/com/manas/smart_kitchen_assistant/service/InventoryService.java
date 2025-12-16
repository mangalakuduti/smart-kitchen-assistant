package com.manas.smart_kitchen_assistant.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.manas.smart_kitchen_assistant.dto.CreateInventoryItemRequest;
import com.manas.smart_kitchen_assistant.dto.InventoryItemResponse;
import com.manas.smart_kitchen_assistant.dto.UpdateInventoryItemRequest;
import com.manas.smart_kitchen_assistant.model.InventoryItem;
import com.manas.smart_kitchen_assistant.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository repository;

    public InventoryItemResponse createItem(CreateInventoryItemRequest req) {

        InventoryItem item = InventoryItem.builder()
                .name(req.getName())
                .quantity(req.getQuantity())
                .unit(req.getUnit())
                .category(req.getCategory())
                .build();

        InventoryItem saved = repository.save(item);

        InventoryItemResponse res = new InventoryItemResponse();
        res.setId(saved.getId());
        res.setName(saved.getName());
        res.setQuantity(saved.getQuantity());
        res.setUnit(saved.getUnit());
        res.setCategory(saved.getCategory());

        return res;
    }

    public List<InventoryItemResponse> getAllItems() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public InventoryItemResponse getItemById(String id) {
        InventoryItem item = repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Inventory Item not found with id: " + id
                )
            );
        return mapToResponse(item);
    }

    public void deleteItemById(String id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Inventory Item not found with id: " + id
            );
        }
        repository.deleteById(id);
    }

    public InventoryItemResponse updateItem(String id, UpdateInventoryItemRequest req) {
        InventoryItem item = repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Inventory Item not found with id: " + id
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

        InventoryItem updated = repository.save(item);
        return mapToResponse(updated);
    }

    private InventoryItemResponse mapToResponse(InventoryItem item) {
        InventoryItemResponse res = new InventoryItemResponse();
        res.setId(item.getId());
        res.setName(item.getName());
        res.setQuantity(item.getQuantity());
        res.setUnit(item.getUnit());
        res.setCategory(item.getCategory());
        return res;
    }
}
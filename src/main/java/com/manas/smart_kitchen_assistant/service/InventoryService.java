package com.manas.smart_kitchen_assistant.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.manas.smart_kitchen_assistant.dto.CreateInventoryItemRequest;
import com.manas.smart_kitchen_assistant.dto.InventoryItemResponse;
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

        // Map entity → response DTO
        InventoryItemResponse res = new InventoryItemResponse();
        res.setId(saved.getId());
        res.setName(saved.getName());
        res.setQuantity(saved.getQuantity());
        res.setUnit(saved.getUnit());
        res.setCategory(saved.getCategory());

        return res;
    }

    public List<InventoryItem> getAllItems() {
        return repository.findAll();
    }
}
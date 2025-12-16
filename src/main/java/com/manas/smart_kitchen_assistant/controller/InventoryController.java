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

import com.manas.smart_kitchen_assistant.dto.CreateInventoryItemRequest;
import com.manas.smart_kitchen_assistant.dto.InventoryItemResponse;
import com.manas.smart_kitchen_assistant.dto.UpdateInventoryItemRequest;
import com.manas.smart_kitchen_assistant.service.InventoryService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
@CrossOrigin // allow React frontend
public class InventoryController {

    private final InventoryService service;

    @PostMapping
    public InventoryItemResponse createItem(@RequestBody CreateInventoryItemRequest req) {
        return service.createItem(req);
    }

    @GetMapping
    public List<InventoryItemResponse> getAllItems() {
        return service.getAllItems();
    }
    @GetMapping("/{id}")
    public InventoryItemResponse getItemById(@PathVariable String id) {
        return service.getItemById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteItemById(@PathVariable String id) {
        service.deleteItemById(id);
    }

    @PatchMapping("/{id}")
    public InventoryItemResponse updateItem(
            @PathVariable String id,
            @RequestBody UpdateInventoryItemRequest req) {
        return service.updateItem(id, req);
    }

    
}
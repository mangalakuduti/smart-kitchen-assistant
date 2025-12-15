package com.manas.smart_kitchen_assistant.repository;

import com.manas.smart_kitchen_assistant.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
}
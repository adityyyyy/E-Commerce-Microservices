package com.springpractice.inventoryservice.service;

import com.springpractice.inventoryservice.model.Inventory;
import com.springpractice.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public Integer isInStock(String skuCode) {
        Optional<Inventory> inventory = inventoryRepository.findBySkuCode(skuCode);
        if (inventory.isPresent()) {
            return inventory.get().getQuantity();
        }
        return 0;
    }
}

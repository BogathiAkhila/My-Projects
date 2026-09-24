package com.intellecommerce.backend.controller;

import com.intellecommerce.backend.domain.Branch;
import com.intellecommerce.backend.domain.Ingredient;
import com.intellecommerce.backend.domain.InventoryLedger;
import com.intellecommerce.backend.domain.MovementType;
import com.intellecommerce.backend.repository.BranchRepository;
import com.intellecommerce.backend.repository.IngredientRepository;
import com.intellecommerce.backend.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    
    private final InventoryService inventoryService;
    private final BranchRepository branchRepository;
    private final IngredientRepository ingredientRepository;

    @PostMapping("/movements")
    public ResponseEntity<InventoryLedger> recordMovement(@RequestBody MovementRequest request) {
        Branch branch = branchRepository.findById(request.branchId()).orElseThrow();
        Ingredient ingredient = ingredientRepository.findById(request.ingredientId()).orElseThrow();
        return ResponseEntity.ok(inventoryService.recordMovement(branch, ingredient, request.type(), request.quantity()));
    }

    @GetMapping("/stock")
    public ResponseEntity<BigDecimal> getStock(@RequestParam UUID branchId, @RequestParam UUID ingredientId) {
        return ResponseEntity.ok(inventoryService.getCurrentStock(ingredientId, branchId));
    }

    public record MovementRequest(UUID branchId, UUID ingredientId, MovementType type, BigDecimal quantity) {}
}
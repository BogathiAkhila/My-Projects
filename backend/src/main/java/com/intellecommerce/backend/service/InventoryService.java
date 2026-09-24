package com.intellecommerce.backend.service;

import com.intellecommerce.backend.domain.*;
import com.intellecommerce.backend.repository.InventoryLedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {
    
    private final InventoryLedgerRepository ledgerRepository;

    @Transactional
    public InventoryLedger recordMovement(Branch branch, Ingredient ingredient, MovementType type, BigDecimal quantity) {
        InventoryLedger entry = new InventoryLedger();
        entry.setBranch(branch);
        entry.setIngredient(ingredient);
        entry.setMovementType(type);
        
        // Automatically make deductions negative to keep the ledger math simple
        if (type == MovementType.SALE_DEDUCTION || type == MovementType.INTER_BRANCH_OUT || type == MovementType.WASTE_LOG) {
            entry.setQuantity(quantity.abs().negate());
        } else {
            entry.setQuantity(quantity.abs());
        }
        
        return ledgerRepository.save(entry);
    }

    @Transactional(readOnly = true)
    public BigDecimal getCurrentStock(UUID ingredientId, UUID branchId) {
        return ledgerRepository.calculateCurrentStock(ingredientId, branchId);
    }
}
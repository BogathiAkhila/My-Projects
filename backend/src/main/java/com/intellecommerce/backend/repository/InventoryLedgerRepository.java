package com.intellecommerce.backend.repository;

import com.intellecommerce.backend.domain.InventoryLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;
import java.math.BigDecimal;

public interface InventoryLedgerRepository extends JpaRepository<InventoryLedger, UUID> {
    
    @Query("SELECT COALESCE(SUM(il.quantity), 0) FROM InventoryLedger il WHERE il.ingredient.id = :ingredientId AND il.branch.id = :branchId")
    BigDecimal calculateCurrentStock(@Param("ingredientId") UUID ingredientId, @Param("branchId") UUID branchId);
}
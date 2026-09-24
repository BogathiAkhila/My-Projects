package com.intellecommerce.backend.repository;

import com.intellecommerce.backend.domain.RecipeVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface RecipeVersionRepository extends JpaRepository<RecipeVersion, UUID> {
    
    // Spring automatically translates this long method name into a valid SQL query!
    Optional<RecipeVersion> findTopByProductIdOrderByVersionNumberDesc(UUID productId);
}
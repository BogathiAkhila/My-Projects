package com.intellecommerce.backend.service;

import com.intellecommerce.backend.domain.RecipeVersion;
import com.intellecommerce.backend.repository.RecipeVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeService {
    
    private final RecipeVersionRepository recipeVersionRepository;

    @Transactional
    public RecipeVersion createNewVersion(UUID productId) {
        // Find the current highest version, or default to version 1 if none exists
        int nextVersion = recipeVersionRepository
            .findTopByProductIdOrderByVersionNumberDesc(productId)
            .map(rv -> rv.getVersionNumber() + 1)
            .orElse(1);
        
        RecipeVersion newVersion = new RecipeVersion();
        newVersion.setProductId(productId);
        newVersion.setVersionNumber(nextVersion);
        
        return recipeVersionRepository.save(newVersion);
    }
}
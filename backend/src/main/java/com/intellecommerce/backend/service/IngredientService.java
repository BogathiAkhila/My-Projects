package com.intellecommerce.backend.service;

import com.intellecommerce.backend.domain.Branch;
import com.intellecommerce.backend.domain.Ingredient;
import com.intellecommerce.backend.repository.BranchRepository;
import com.intellecommerce.backend.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final BranchRepository branchRepository;

    public Ingredient createIngredient(UUID branchId, String name) {
        Branch branch = branchRepository.findById(branchId)
            .orElseThrow(() -> new RuntimeException("Branch not found"));
        Ingredient ingredient = new Ingredient();
        ingredient.setBranch(branch);
        ingredient.setName(name);
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(UUID id) {
        // This triggers the @SQLDelete annotation for soft deletion!
        ingredientRepository.deleteById(id);
    }
}

package com.intellecommerce.backend.controller;

import com.intellecommerce.backend.domain.Ingredient;
import com.intellecommerce.backend.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    
    private final IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody IngredientRequest request) {
        return ResponseEntity.ok(ingredientService.createIngredient(request.branchId(), request.name()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable UUID id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.ok().build();
    }

    public record IngredientRequest(UUID branchId, String name) {}
}

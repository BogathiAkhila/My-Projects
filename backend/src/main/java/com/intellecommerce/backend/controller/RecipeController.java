package com.intellecommerce.backend.controller;

import com.intellecommerce.backend.domain.RecipeVersion;
import com.intellecommerce.backend.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {
    
    private final RecipeService recipeService;

    @PostMapping("/new-version")
    public ResponseEntity<RecipeVersion> createNewVersion(@RequestBody RecipeRequest request) {
        return ResponseEntity.ok(recipeService.createNewVersion(request.productId()));
    }

    public record RecipeRequest(UUID productId) {}
}
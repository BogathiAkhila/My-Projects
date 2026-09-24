package com.intellecommerce.backend.controller;

import com.intellecommerce.backend.domain.Branch;
import com.intellecommerce.backend.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {
    
    private final BranchService branchService;

    @PostMapping
    public ResponseEntity<Branch> createBranch(@RequestBody BranchRequest request) {
        return ResponseEntity.ok(branchService.createBranch(request.tenantId(), request.name()));
    }

    public record BranchRequest(UUID tenantId, String name) {}
}
package com.intellecommerce.backend.controller;

import com.intellecommerce.backend.domain.Tenant;
import com.intellecommerce.backend.dto.TenantRequest;
import com.intellecommerce.backend.service.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenants")
@RequiredArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping
    public ResponseEntity<Tenant> createTenant(@RequestBody TenantRequest request) {
        Tenant newTenant = tenantService.createTenant(request.name());
        return ResponseEntity.ok(newTenant);
    }
}
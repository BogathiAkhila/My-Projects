package com.intellecommerce.backend.service;

import com.intellecommerce.backend.domain.Branch;
import com.intellecommerce.backend.domain.Tenant;
import com.intellecommerce.backend.repository.BranchRepository;
import com.intellecommerce.backend.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchService {
    private final BranchRepository branchRepository;
    private final TenantRepository tenantRepository;

    public Branch createBranch(UUID tenantId, String name) {
        Tenant tenant = tenantRepository.findById(tenantId)
            .orElseThrow(() -> new RuntimeException("Tenant not found"));
        Branch branch = new Branch();
        branch.setTenant(tenant);
        branch.setName(name);
        return branchRepository.save(branch);
    }
}
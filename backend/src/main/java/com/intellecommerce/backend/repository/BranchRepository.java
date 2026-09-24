package com.intellecommerce.backend.repository;
import com.intellecommerce.backend.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BranchRepository extends JpaRepository<Branch, UUID> {}
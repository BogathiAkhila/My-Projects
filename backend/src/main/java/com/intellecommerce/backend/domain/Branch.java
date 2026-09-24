package com.intellecommerce.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "branches")
@Getter
@Setter
@SQLDelete(sql = "UPDATE branches SET is_active = false WHERE id=?")
@SQLRestriction("is_active = true")
public class Branch extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
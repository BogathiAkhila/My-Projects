package com.intellecommerce.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "tenants")
@Getter
@Setter
@SQLDelete(sql = "UPDATE tenants SET is_active = false WHERE id=?")
@SQLRestriction("is_active = true")
public class Tenant extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
package com.intellecommerce.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.util.UUID;

@Entity
@Table(name = "recipe_versions")
@Getter
@Setter
@SQLDelete(sql = "UPDATE recipe_versions SET is_active = false WHERE id=?")
@SQLRestriction("is_active = true")
public class RecipeVersion extends BaseEntity {

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}

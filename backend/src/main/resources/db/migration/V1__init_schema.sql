-- 1. Foundation: Tenant & Branch Isolation
CREATE TABLE tenants (
    id BINARY(16) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE branches (
    id BINARY(16) PRIMARY KEY,
    tenant_id BINARY(16) NOT NULL,
    name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_branch_tenant FOREIGN KEY (tenant_id) REFERENCES tenants(id)
);

-- 2. Master Data with Soft Deletion Semantics
CREATE TABLE ingredients (
    id BINARY(16) PRIMARY KEY,
    branch_id BINARY(16) NOT NULL,
    name VARCHAR(255) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_ingredient_branch FOREIGN KEY (branch_id) REFERENCES branches(id)
);

-- 3. Recipe Versioning (Historical Isolation)
CREATE TABLE recipe_versions (
    id BINARY(16) PRIMARY KEY,
    product_id BINARY(16) NOT NULL,
    version_number INT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 4. Immutable Inventory Ledger (Append-Only)
CREATE TABLE inventory_ledger (
    id BINARY(16) PRIMARY KEY,
    branch_id BINARY(16) NOT NULL,
    ingredient_id BINARY(16) NOT NULL,
    movement_type VARCHAR(50) NOT NULL,
    quantity DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_ledger_branch FOREIGN KEY (branch_id) REFERENCES branches(id),
    CONSTRAINT fk_ledger_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredients(id)
);
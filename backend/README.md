# Enterprise Restaurant Backend

A robust, multi-tenant Spring Boot architecture built to guarantee 100% financial traceability using an append-only ledger system.

## Core Architecture Features
1. **Strict Domain Isolation:** Enforced via `tenant_id` and `branch_id` mappings to ensure data security across restaurant locations.
2. **Append-Only Inventory Ledger:** No inventory data is ever overwritten. Current stock is calculated dynamically via JPQL summation of historical receipts and waste deductions.
3. **Soft-Deletion Semantics:** Handled globally via Hibernate `@SQLDelete` and `@SQLRestriction` to preserve historical financial integrity when ingredients are removed.
4. **Recipe Versioning:** Historical isolation ensuring past cost calculations remain perfectly accurate even when recipes change over time.

## How to Run This Project

**Prerequisites:** Java 21+ and MySQL 8+

1. Open MySQL Workbench and create the blank database:
   `CREATE DATABASE intellecommerce;`
2. Update the `src/main/resources/application.yml` with your local MySQL username and password.
3. Run `BackendApplication.java`. Flyway will automatically execute the schema migration on startup.
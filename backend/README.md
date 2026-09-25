# Enterprise Restaurant Backend

A robust, multi-tenant microservice architecture built to guarantee 100% financial traceability using an append-only ledger system. Designed for enterprise-scale restaurant chains, this backend ensures strict domain isolation across branches while maintaining impeccable historical data integrity.

## 🏗️ Architecture & Core Features

* **Strict Domain Isolation:** Enforced via `tenant_id` and `branch_id` mapping to ensure absolute data security and logical separation across different restaurant locations.
* **Append-Only Inventory Ledger:** No inventory data is ever overwritten. Current stock is dynamically calculated via JPQL summation of historical receipts, consumption, and waste deductions.
* **Soft-Deletion Semantics:** Handled globally via Hibernate `@SQLDelete` and `@SQLRestriction`. Preserves historical financial integrity and relationship mapping even when ingredients or menus are removed.
* **Recipe Versioning:** Employs historical isolation, ensuring past food cost calculations and financial reports remain perfectly accurate even when recipes change over time.

## 💻 Tech Stack

* **Core:** Java 21, Spring Boot, Spring Data JPA
* **Database:** MySQL 8.0, Flyway Migrations (Automated schema generation)
* **DevOps:** Docker, Docker Compose (Multi-stage builds)

## 🚀 Quick Start (Dockerized Deployment)

This project is fully containerized for seamless evaluation. **You do not need Java, Maven, or a local MySQL server installed** to run this application. 

### 1. Start the Environment
Open your terminal in the `backend` directory (where `docker-compose.yml` is located) and run:
```bash
docker-compose up --build -d
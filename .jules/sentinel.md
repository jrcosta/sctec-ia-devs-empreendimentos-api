## 2026-02-26 - Schema Validation Blocking Testing
**Vulnerability:** The database schema (Flyway migration V1) did not match the JPA Entity definition (`Empreendimento.java`), causing `SchemaManagementException` during test execution (`spring.jpa.hibernate.ddl-auto=validate`).
**Learning:** In strict environments where schema validation is enabled, missing database columns (even if unused by business logic) will prevent the application context from starting, blocking automated security testing.
**Prevention:** Always ensure Flyway migrations are updated in sync with Entity changes. Added `V3__add_data_atualizacao.sql` to align the schema with the existing entity.

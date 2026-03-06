## 2026-02-26 - Schema Validation Blocking Testing
**Vulnerability:** The database schema (Flyway migration V1) did not match the JPA Entity definition (`Empreendimento.java`), causing `SchemaManagementException` during test execution (`spring.jpa.hibernate.ddl-auto=validate`).
**Learning:** In strict environments where schema validation is enabled, missing database columns (even if unused by business logic) will prevent the application context from starting, blocking automated security testing.
**Prevention:** Always ensure Flyway migrations are updated in sync with Entity changes. Added `V3__add_data_atualizacao.sql` to align the schema with the existing entity.
# Sentinel Journal 🛡️

## 2026-02-25 - Missing Input Length Validation
**Vulnerability:** Input fields in `EmpreendimentoRequestDTO` lacked length validation (`@Size`), allowing arbitrary length strings to be passed to the service layer. This caused `DataIntegrityViolationException` at the database level when persisting entities, resulting in 500 Internal Server Errors instead of 400 Bad Requests.
**Learning:** JPA `@Column(length=X)` annotations only enforce schema constraints but do not perform pre-persistence validation in the application layer unless Hibernate Validator is explicitly invoked or the DTO mirrors these constraints.
**Prevention:** Always mirror database constraints (length, nullability) in DTOs using Jakarta Validation annotations (`@Size`, `@NotNull`) to fail fast and securely at the controller level.

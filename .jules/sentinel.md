## 2026-02-27 - Input Validation Mismatch & Schema Drift

**Vulnerability:**
The `EmpreendimentoRequestDTO` lacked `@Size` validation annotations, while the underlying `Empreendimento` entity and database schema had strict length constraints (e.g., `VARCHAR(50)` for contact).
This allowed an attacker to send oversized payloads that would bypass application-layer validation and trigger a `DataIntegrityViolationException` (DB level error), resulting in a `500 Internal Server Error` and potential stack trace leakage, rather than a proper `400 Bad Request`.

**Learning:**
Security isn't just about sanitized queries; it's about failing gracefully. Relying on the database to enforce constraints is a vulnerability because it leaks implementation details and consumes unnecessary resources.
Additionally, strict schema validation (`ddl-auto=validate`) in Spring Boot can expose hidden schema drifts (like the missing `data_atualizacao` column) that prevent the application from starting in secure environments, forcing immediate remediation.

**Prevention:**
1. Always mirror database constraints (length, nullability) in DTOs using Bean Validation (`@Size`, `@NotNull`).
2. Use `MockMvc` tests to verify that invalid inputs return `400` and not `500`.
3. Keep Flyway migrations strictly in sync with JPA entities to avoid startup failures in production-like environments.
## 2026-02-26 - Schema Validation Blocking Testing
**Vulnerability:** The database schema (Flyway migration V1) did not match the JPA Entity definition (`Empreendimento.java`), causing `SchemaManagementException` during test execution (`spring.jpa.hibernate.ddl-auto=validate`).
**Learning:** In strict environments where schema validation is enabled, missing database columns (even if unused by business logic) will prevent the application context from starting, blocking automated security testing.
**Prevention:** Always ensure Flyway migrations are updated in sync with Entity changes. Added `V3__add_data_atualizacao.sql` to align the schema with the existing entity.
# Sentinel Journal 🛡️

## 2026-02-25 - Missing Input Length Validation
**Vulnerability:** Input fields in `EmpreendimentoRequestDTO` lacked length validation (`@Size`), allowing arbitrary length strings to be passed to the service layer. This caused `DataIntegrityViolationException` at the database level when persisting entities, resulting in 500 Internal Server Errors instead of 400 Bad Requests.
**Learning:** JPA `@Column(length=X)` annotations only enforce schema constraints but do not perform pre-persistence validation in the application layer unless Hibernate Validator is explicitly invoked or the DTO mirrors these constraints.
**Prevention:** Always mirror database constraints (length, nullability) in DTOs using Jakarta Validation annotations (`@Size`, `@NotNull`) to fail fast and securely at the controller level.

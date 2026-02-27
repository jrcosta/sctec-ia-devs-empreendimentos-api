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

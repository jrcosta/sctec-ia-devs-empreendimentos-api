## 2026-03-04 - Missing Database Length Validation

**Vulnerability:** The `EmpreendimentoRequestDTO` lacked `@Size` validation limits, allowing arbitrary length strings.

**Learning:** This exposes the application to `DataIntegrityViolationException` when strings longer than the database schema limits (100 characters for names, 50 for contato) are submitted. This can also lead to Denial of Service (DoS) attacks via overly large payloads and ungraceful error handling. Additionally, the application failed to start due to `spring.jpa.hibernate.ddl-auto=validate` detecting a missing `data_atualizacao` column in the database, which was defined in the `Empreendimento` entity but not in the initial Flyway migration scripts.

**Prevention:** Always ensure DTO validation rules strictly match or are stricter than the underlying database schema constraints. For example, use `@Size(max = 100)` on fields defined as `VARCHAR(100)`. When adding new entity fields, always create corresponding Flyway migration scripts to ensure the schema matches the entity definition.

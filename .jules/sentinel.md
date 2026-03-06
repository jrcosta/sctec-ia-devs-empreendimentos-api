## 2023-10-27 - [Application level constraint validation matching Database limits]
**Vulnerability:** Input fields lacking length validation on DTOs mismatch the actual table schemas. When a string larger than expected is inserted, the DB engine throws a `DataIntegrityViolationException`.
**Learning:** This leads to an unhandled exception at runtime and causes the application to respond with a 500 Internal Server error possibly logging sensitive implementations if stack traces are exposed. Furthermore, it causes database DoS as large payloads reach the DB before failing.
**Prevention:** We should always define length rules `@Size` directly within Request DTO objects. So that `GlobalExceptionHandler` and `MethodArgumentNotValidException` can handle the constraint correctly returning a well-formatted 400 Bad Request error.
## 2024-05-20 - [Stack Trace Leakage and DoS via Missing Input Validation]
**Vulnerability:** The application was vulnerable to stack trace leakage on unhandled exceptions (e.g., `DataIntegrityViolationException`) and potential Denial of Service (DoS) due to unbounded input strings reaching the database. The `EmpreendimentoRequestDTO` lacked `@Size` constraints despite the database schema defining strict lengths (e.g., `VARCHAR(100)`). Additionally, `GlobalExceptionHandler` did not have a catch-all generic handler.
**Learning:** Spring Boot's default error handling behavior may leak stack traces or internal implementation details if a generic catch-all exception handler is not defined. Furthermore, relying solely on database constraints to enforce data integrity exposes the application to unhandled `DataIntegrityViolationException`s, which can cause internal errors and unnecessary database load.
**Prevention:** Always mirror database schema constraints (like string length) as validation constraints (e.g., `@Size`) on the input DTOs. Implement a global exception handler (`@RestControllerAdvice`) with an `@ExceptionHandler(Exception.class)` that intercepts any unhandled exceptions and returns a generic, sanitized 500 Internal Server Error message.
## 2026-02-28 - [Fixed database constraints validation]
**Vulnerability:** Input fields lacked the necessary maximum length validation to ensure data wasn't truncated or caused database errors upon insertion. Length mismatch between entity annotations, validation, and database schemas could lead to exceptions.
**Learning:** Adding maximum length validation checks corresponding to database schemas allows for earlier detection and clearer user errors rather than unhandled database constraint exceptions.
**Prevention:** Make sure DTOs specify constraints (e.g. `@Size`) on inputs matching columns properties and constraints.
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

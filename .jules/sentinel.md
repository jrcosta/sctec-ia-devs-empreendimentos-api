## 2026-03-06 - [Global Exception Handling]
**Vulnerability:** Missing error handling exposing stack traces (Information Leakage).
**Learning:** Spring Boot's default error handling might expose too much detail or stack traces in unhandled exceptions depending on the configuration. A catch-all handler for `Exception.class` ensures no unhandled application error details reach the end user.
**Prevention:** Implement a `@RestControllerAdvice` with an `@ExceptionHandler(Exception.class)` that logs the full exception on the server side but returns a sanitized, generic 500 response to the client.

## 2026-02-28 - [Fixed database constraints validation]
**Vulnerability:** Input fields lacked the necessary maximum length validation to ensure data wasn't truncated or caused database errors upon insertion. Length mismatch between entity annotations, validation, and database schemas could lead to exceptions.
**Learning:** Adding maximum length validation checks corresponding to database schemas allows for earlier detection and clearer user errors rather than unhandled database constraint exceptions.
**Prevention:** Make sure DTOs specify constraints (e.g. `@Size`) on inputs matching columns properties and constraints.

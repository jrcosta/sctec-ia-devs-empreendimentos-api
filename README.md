# SCTEC - API de Gerenciamento de Empreendimentos

> Desafio **IA para DEVs** — SCTEC / LAB365

## 📌 Descrição do Projeto

API RESTful para gerenciar dados de **empreendimentos no estado de Santa Catarina**, oferecendo operações completas de CRUD (Create, Read, Update, Delete) sobre os cadastros de organizações e negócios da região catarinense.

A arquitetura segue o padrão de camadas (Controller → Service → Repository) com validações de dados via Bean Validation, tratamento centralizado de exceções, migrações de banco gerenciadas pelo Flyway e documentação interativa via Swagger/OpenAPI.

---

## 🚀 Tecnologias Utilizadas

| Tecnologia | Versão / Detalhes |
|---|---|
| **Java** | 17 (LTS) |
| **Spring Boot** | 3.3.3 |
| **Spring Web** | REST Controllers com anotações `@RestController`, `@GetMapping`, etc. |
| **Spring Data JPA** | Abstração de persistência sobre Hibernate |
| **Spring Validation** | Bean Validation (Hibernate Validator) nos DTOs |
| **Flyway** | Migrações versionadas de banco de dados (DDL + Seed) |
| **PostgreSQL** | 15 (via Docker) — banco de produção |
| **H2 Database** | In-memory — fallback para desenvolvimento local/testes |
| **Lombok** | Redução de boilerplate (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`) |
| **Springdoc OpenAPI** | 2.6.0 — Swagger UI para documentação interativa |
| **JUnit 5 + Mockito** | Testes unitários de Controller (`@WebMvcTest`) e Service |
| **Docker** | Dockerfile multi-stage + Docker Compose |
| **Maven** | Gerenciamento de dependências (wrapper `mvnw` incluído) |

---

## 📂 Estrutura do Projeto

```text
sctec-ia-devs-empreendimentos-api/
├── Dockerfile                         # Build multi-stage (JDK → JRE Alpine)
├── docker-compose.yml                 # Orquestração: PostgreSQL 15 + API
├── pom.xml                            # Dependências Maven
├── docs/                              # Documentação complementar
│   ├── diretrizes-agentes-ia/         # Regras e plano do desafio SCTEC
│   ├── docker-postgres.md             # Detalhes da containerização
│   ├── guia-uso-postman.md            # Guia de importação e uso do Postman
│   └── *.postman_collection.json      # Coleção Postman exportada (5 requests)
└── src/
    ├── main/
    │   ├── java/com/sctec/api/
    │   │   ├── ApiApplication.java         # Classe principal (@SpringBootApplication)
    │   │   ├── controller/
    │   │   │   └── EmpreendimentoController.java   # 5 endpoints REST
    │   │   ├── dto/
    │   │   │   ├── EmpreendimentoRequestDTO.java    # DTO de entrada (com validações)
    │   │   │   └── EmpreendimentoResponseDTO.java   # DTO de saída
    │   │   ├── entity/
    │   │   │   └── Empreendimento.java              # Entidade JPA mapeada
    │   │   ├── enums/
    │   │   │   ├── Segmento.java    # TECNOLOGIA, COMERCIO, INDUSTRIA, SERVICOS, AGRONEGOCIO
    │   │   │   └── Status.java      # ATIVO, INATIVO
    │   │   ├── exception/
    │   │   │   ├── GlobalExceptionHandler.java      # @RestControllerAdvice (404 + 400)
    │   │   │   └── ResourceNotFoundException.java   # Exceção customizada
    │   │   ├── repository/
    │   │   │   └── EmpreendimentoRepository.java    # JpaRepository<Empreendimento, Long>
    │   │   └── service/
    │   │       ├── EmpreendimentoService.java        # Interface do serviço
    │   │       └── EmpreendimentoServiceImpl.java    # Implementação com @Transactional
    │   └── resources/
    │       ├── application.properties               # Config flexível (H2 ↔ PostgreSQL)
    │       └── db/migration/
    │           ├── V1__create_table_empreendimentos.sql   # DDL da tabela
    │           └── V2__insert_seed_empreendimentos.sql    # 3 registros iniciais
    └── test/java/com/sctec/api/
        ├── ApiApplicationTests.java
        ├── controller/
        │   └── EmpreendimentoControllerTest.java    # 7 testes (MockMvc)
        └── service/
            └── EmpreendimentoServiceImplTest.java   # 7 testes (Mockito)
```

---

## 🐳 Executando via Docker Compose (Recomendado)

A forma mais rápida e prática de subir toda a aplicação — **sem precisar instalar Java, Maven ou PostgreSQL** na sua máquina.

### Pré-requisitos

- **Docker** e **Docker Compose** instalados e em execução.

### Passo a Passo

**1. Clone o repositório:**

```bash
git clone https://github.com/jrcosta/sctec-ia-devs-empreendimentos-api.git
cd sctec-ia-devs-empreendimentos-api
```

**2. Suba os containers (build + execução):**

```bash
docker compose up --build -d
```

Este comando irá:
- Construir a imagem da API via Dockerfile multi-stage (compilação Maven + empacotamento `.jar`)
- Subir o banco **PostgreSQL 15** (Alpine) com volume persistente
- Iniciar a **API Spring Boot** conectada ao PostgreSQL
- Executar as migrações Flyway automaticamente (criação da tabela + seed de dados)

**3. Verifique se está funcionando:**

```bash
# Logs da API em tempo real
docker compose logs -f api

# Teste rápido no navegador ou terminal
curl http://localhost:8080/api/v1/empreendimentos
```

A API responderá com os 3 empreendimentos pré-cadastrados pelo seed.

**4. Acesse a documentação interativa (Swagger):**

Abra no navegador: **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

**5. Para parar e limpar:**

```bash
# Parar os containers (preserva dados do banco)
docker compose stop

# Parar e remover containers + volumes (limpa tudo)
docker compose down -v
```

### Variáveis de Ambiente (docker-compose.yml)

| Variável | Valor Padrão | Descrição |
|---|---|---|
| `POSTGRES_USER` | `sctec_user` | Usuário do banco PostgreSQL |
| `POSTGRES_PASSWORD` | `sctec_password` | Senha do banco PostgreSQL |
| `POSTGRES_DB` | `sctec_empreendimentos` | Nome do banco de dados |
| `DB_URL` | `jdbc:postgresql://db:5432/sctec_empreendimentos` | JDBC URL usada pela API |
| `DB_USERNAME` | `sctec_user` | Usuário de conexão JPA |
| `DB_PASSWORD` | `sctec_password` | Senha de conexão JPA |
| `DB_DRIVER` | `org.postgresql.Driver` | Driver JDBC |
| `DB_DIALECT` | `org.hibernate.dialect.PostgreSQLDialect` | Dialeto Hibernate |

### Arquitetura dos Containers

```text
┌──────────────────────────────────────────────────────┐
│                  docker compose                      │
│                                                      │
│  ┌──────────────────┐     ┌────────────────────────┐ │
│  │    sctec_api      │     │      sctec_db          │ │
│  │  Spring Boot 3    │────▶│   PostgreSQL 15        │ │
│  │  Java 17 (JRE)   │     │   Alpine               │ │
│  │  Porta: 8080      │     │   Porta: 5432          │ │
│  └──────────────────┘     └────────────────────────┘ │
│         │                        │                    │
│         ▼                        ▼                    │
│   localhost:8080            pgdata (volume)           │
└──────────────────────────────────────────────────────┘
```

---

## ⚙️ Executando Localmente (Sem Docker)

Para executar o sistema diretamente na máquina, **sem Docker**.

### Pré-requisitos

- **Java JDK 17** (ou superior) instalado e configurado no `JAVA_HOME`.
- **Maven** (opcional — o wrapper `mvnw` está incluído no projeto).

### Inicialização

```bash
# 1. Clone o repositório
git clone https://github.com/jrcosta/sctec-ia-devs-empreendimentos-api.git
cd sctec-ia-devs-empreendimentos-api

# 2. Instale as dependências e gere o pacote (.jar)
./mvnw clean install

# 3. Execute a aplicação
./mvnw spring-boot:run
```

> **Nota:** Sem Docker, a aplicação utiliza automaticamente o banco **H2 in-memory** como fallback. Os dados são reinicializados a cada reinício da aplicação.

A API estará disponível em: **http://localhost:8080**

---

## 🌐 Endpoints da API

Todos os endpoints estão sob o prefixo `/api/v1/empreendimentos`.

### Resumo

| Método | Rota | Descrição | HTTP Status |
|---|---|---|---|
| `POST` | `/api/v1/empreendimentos` | Criar novo empreendimento | `201 Created` |
| `GET` | `/api/v1/empreendimentos` | Listar todos (paginado) | `200 OK` |
| `GET` | `/api/v1/empreendimentos/{id}` | Buscar por ID | `200 OK` / `404 Not Found` |
| `PUT` | `/api/v1/empreendimentos/{id}` | Atualizar por ID | `200 OK` / `404 Not Found` |
| `DELETE` | `/api/v1/empreendimentos/{id}` | Excluir por ID | `204 No Content` / `404 Not Found` |

### Detalhes e Exemplos

#### 1️⃣ Criar Empreendimento — `POST /api/v1/empreendimentos`

**Request Body (JSON):**

```json
{
  "nomeEmpreendimento": "Innova SC Tech",
  "nomeEmpreendedor": "Maria Souza",
  "municipioSC": "Florianópolis",
  "segmento": "TECNOLOGIA",
  "contato": "(48) 99999-1111",
  "status": "ATIVO"
}
```

**Response — `201 Created`:**

```json
{
  "id": 4,
  "nomeEmpreendimento": "Innova SC Tech",
  "nomeEmpreendedor": "Maria Souza",
  "municipioSC": "Florianópolis",
  "segmento": "TECNOLOGIA",
  "contato": "(48) 99999-1111",
  "status": "ATIVO",
  "dataCadastro": "2026-03-13T18:00:00",
  "dataAtualizacao": null
}
```

> **Campos obrigatórios:** `nomeEmpreendimento`, `nomeEmpreendedor`, `municipioSC`, `segmento`, `contato`, `status`.
>
> **Valores válidos para `segmento`:** `TECNOLOGIA`, `COMERCIO`, `INDUSTRIA`, `SERVICOS`, `AGRONEGOCIO`
>
> **Valores válidos para `status`:** `ATIVO`, `INATIVO`

#### 2️⃣ Listar Empreendimentos — `GET /api/v1/empreendimentos`

Retorna uma lista paginada. Parâmetros opcionais de query: `page` (padrão `0`), `size` (padrão `20`), `sort`.

```bash
curl "http://localhost:8080/api/v1/empreendimentos?page=0&size=10"
```

#### 3️⃣ Buscar por ID — `GET /api/v1/empreendimentos/{id}`

```bash
curl http://localhost:8080/api/v1/empreendimentos/1
```

Retorna `200 OK` com o objeto, ou `404 Not Found` se o ID não existir.

#### 4️⃣ Atualizar Empreendimento — `PUT /api/v1/empreendimentos/{id}`

```bash
curl -X PUT http://localhost:8080/api/v1/empreendimentos/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nomeEmpreendimento": "Innova SC Tech Atualizado",
    "nomeEmpreendedor": "Maria Souza",
    "municipioSC": "Joinville",
    "segmento": "TECNOLOGIA",
    "contato": "(47) 98888-0000",
    "status": "INATIVO"
  }'
```

Retorna `200 OK` com o objeto atualizado e o campo `dataAtualizacao` preenchido.

#### 5️⃣ Excluir Empreendimento — `DELETE /api/v1/empreendimentos/{id}`

```bash
curl -X DELETE http://localhost:8080/api/v1/empreendimentos/1
```

Retorna `204 No Content` em caso de sucesso, ou `404 Not Found` se o ID for inexistente.

---

## 🛡️ Tratamento de Erros

A API possui tratamento centralizado de exceções via `GlobalExceptionHandler`:

| Cenário | HTTP Status | Exemplo de Resposta |
|---|---|---|
| Recurso não encontrado | `404 Not Found` | `{"timestamp": "...", "status": 404, "error": "Not Found", "message": "Empreendimento não encontrado para o id 99"}` |
| Validação de campos | `400 Bad Request` | `{"timestamp": "...", "status": 400, "error": "Bad Request", "message": "Validation Failed", "details": {"nomeEmpreendimento": "O nome do empreendimento não pode estar em branco"}}` |

---

## 🗃️ Migrações do Banco de Dados (Flyway)

O projeto utiliza **Flyway** para versionamento do schema. As migrações ficam em `src/main/resources/db/migration/`:

| Migração | Descrição |
|---|---|
| `V1__create_table_empreendimentos.sql` | Cria a tabela `empreendimentos` com todos os campos necessários |
| `V2__insert_seed_empreendimentos.sql` | Insere 3 registros iniciais para facilitar testes e desenvolvimento |

As migrações são executadas automaticamente na inicialização da aplicação, tanto com H2 quanto com PostgreSQL.

---

## 📖 Documentação Interativa (Swagger / OpenAPI)

Com a aplicação rodando, acesse:

- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **OpenAPI JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## 🧪 Testes Automatizados

O projeto inclui **14 testes unitários** cobrindo as camadas de Controller e Service:

| Arquivo | Tipo | Quantidade | Framework |
|---|---|---|---|
| `EmpreendimentoControllerTest` | `@WebMvcTest` | 7 testes | MockMvc + Mockito |
| `EmpreendimentoServiceImplTest` | `@ExtendWith(MockitoExtension)` | 7 testes | JUnit 5 + Mockito |

**Cenários cobertos:** criação, listagem paginada, busca por ID, atualização, exclusão, validação de campos inválidos e tratamento de recurso não encontrado.

Para executar os testes:

```bash
./mvnw test
```

---

## 📬 Testes com Postman

O projeto inclui uma coleção Postman pronta para uso:

1. Importe o arquivo `docs/sctec-ia-devs-empreendimentos-api.postman_collection.json` no Postman.
2. A variável `base_url` já vem configurada para `http://localhost:8080`.
3. Os 5 endpoints estão pré-configurados com payloads de exemplo.

Consulte o guia completo em: [`docs/guia-uso-postman.md`](docs/guia-uso-postman.md)

---

## 📚 Documentação Complementar

| Documento | Descrição |
|---|---|
| [`docs/docker-postgres.md`](docs/docker-postgres.md) | Detalhes técnicos da containerização, parametrização e Flyway |
| [`docs/guia-uso-postman.md`](docs/guia-uso-postman.md) | Guia de importação e uso da coleção Postman |
| [`docs/diretrizes-agentes-ia/`](docs/diretrizes-agentes-ia/) | Documento oficial do desafio e plano de implementação |

---

## 🎥 Link do Vídeo Pitch

Segue o link do vídeo final (de 3 minutos) sobre a apresentação oficial entregue:

> [Assista ao vídeo pitch do Desafio SCTEC Backend (YouTube/Vimeo)]()

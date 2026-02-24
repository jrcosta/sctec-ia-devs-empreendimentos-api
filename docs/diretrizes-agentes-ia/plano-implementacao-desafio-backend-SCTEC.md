# Plano detalhado --- Desafio de Software (Backend / API REST) --- IA para DEVs (SCTEC)

## Objetivo

Construir uma API REST CRUD para gerenciamento de empreendimentos em
Santa Catarina, com foco em aderência total ao escopo e nota máxima.

------------------------------------------------------------------------

## 1) Requisitos do desafio

### Campos obrigatórios

-   nomeEmpreendimento
-   nomeEmpreendedor
-   municipioSC
-   segmento (TECNOLOGIA, COMERCIO, INDUSTRIA, SERVICOS, AGRONEGOCIO)
-   contato
-   status (ATIVO / INATIVO)

### Funcionalidades obrigatórias

-   Create
-   Read
-   Update
-   Delete

------------------------------------------------------------------------

## 2) Stack recomendada

-   Java 17+
-   Spring Boot
-   Spring Web
-   Spring Data JPA
-   Spring Validation
-   H2 ou PostgreSQL
-   JUnit 5
-   Swagger (opcional)
-   Docker (opcional)

------------------------------------------------------------------------

## 3) Arquitetura sugerida

src/main/java/.../empreendimentos - controller - dto - entity -
repository - service - exception

------------------------------------------------------------------------

## 4) Endpoints

Base: /api/v1/empreendimentos

-   POST /
-   GET /
-   GET /{id}
-   PUT /{id}
-   DELETE /{id}

------------------------------------------------------------------------

## 5) Plano de branches

-   main
-   develop
-   feature/domain-entity
-   feature/crud-endpoints
-   feature/validation-errorhandling
-   feature/docs-readme

------------------------------------------------------------------------

## 6) Plano de commits (mínimo 10)

1.  bootstrap project
2.  add entity and enums
3.  add repository
4.  add DTOs and validation
5.  implement service layer
6.  add CRUD endpoints
7.  add exception handler
8.  add tests
9.  write README
10. final cleanup

------------------------------------------------------------------------

## 7) Passo a passo

### Etapa A --- Setup

-   Criar projeto Spring Boot
-   Configurar banco
-   Subir aplicação

### Etapa B --- Domínio

-   Criar entidade
-   Criar enums
-   Criar repository

### Etapa C --- Service + Controller

-   Implementar CRUD completo
-   Validar dados
-   Retornar status HTTP corretos

### Etapa D --- Qualidade

-   Adicionar tratamento de erros
-   Testes básicos
-   Swagger (opcional)
-   Docker (opcional)

------------------------------------------------------------------------

## 8) README deve conter

-   Descrição do projeto
-   Tecnologias utilizadas
-   Estrutura do projeto
-   Como rodar
-   Exemplos de endpoints
-   Link do vídeo pitch

Meta: mais de 1200 caracteres.

------------------------------------------------------------------------

## 9) Vídeo pitch (até 3 minutos)

-   Apresentação
-   Stack e arquitetura
-   Demonstração do CRUD
-   Decisões técnicas
-   Encerramento

------------------------------------------------------------------------

## 10) Checklist final

-   Repositório público no GitHub
-   README completo
-   Link do vídeo no README
-   Mais de 3 branches
-   Mais de 8 commits
-   CRUD totalmente funcional

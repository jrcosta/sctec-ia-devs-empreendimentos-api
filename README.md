# SCTEC - Gerenciamento de Empreendimentos (Desafio OpenAI para DEVs)

## 📌 Descrição do Projeto

Este projeto consiste em uma API RESTful desenvolvida para gerenciar os dados de **empreendimentos no estado de Santa Catarina**. O propósito fundamental do sistema é disponibilizar operações completas de CRUD (Create, Read, Update, Delete) que possibilitem o cadastro, edição, exclusão e visualização de organizações e negócios espalhados pela região catarinense.

A construção deste serviço seguiu de forma estrita e direta as premissas, regras e avaliações requeridas no desafio da SCTEC (IA para DEVs). A arquitetura implementada reflete a adoção das melhores práticas conhecidas na stack Java + Spring Boot, objetivando manutenibilidade, agilidade e escalabilidade da aplicação. O projeto inclui separação em camadas (Controller, Service, Repository) e está instrumentado com validações de dados e tratamento adequado das requisições HTTP e de exceções de domínio.

## 🚀 Tecnologias Utilizadas

A solução backend desenvolvida explora o poder de bibliotecas consolidadas e modernas do ecossistema Java, descritas abaixo:

- **Java 17+**: A versão Long-Term Support (LTS) utilizada como base sólida, robusta e escalável da aplicação.
- **Spring Boot 3.x**: O framework nuclear que gerencia desde a autoconfiguração do servidor à facilidade na injeção de dependências.
- **Spring Web**: Permite o mapeamento veloz e simplificado de endpoints REST através das de anotações focadas (`@RestController`, `@GetMapping`, etc).
- **Spring Data JPA**: Abstração sobre a persistência dos dados que reduz significativamente o código de repetição e unifica o uso com o Hibernate.
- **Spring Validation (Hibernate Validator)**: Aplicação simples das regras de validação sobre o payload antes que ele chegue ao processamento da regra de negócio.
- **Banco de Dados (H2 / PostgreSQL)**: O banco utilizado foi estrategicamente estruturado. Durante o desenvolvimento e em perfis de teste, o "In-Memory" (H2) agiliza as validações.
- **Lombok**: Biblioteca utilitária incorporada para reduzir a verbosidade de modelagens, eliminando Getters, Setters e Construtores manuais.
- **JUnit 5**: Para testes funcionais da camada REST.
- **Swagger / OpenAPI**: Ferramenta acoplada ao framework capaz de gerar a visualização das rotas, retornos e tipos expostos da aplicação.

## 📂 Estrutura do Projeto

A organização de diretórios seguiu a convenção de "layers" do mundo corporativo Java:

```text
src/main/java/com/sctec/api/.../empreendimentos/
├── controller/    # Configuração e definição dos endpoints que ouvem requisições HTTP
├── dto/           # Pattern de Transferência de Dados; encapsula a payload para entrada e saída 
├── entity/        # Modelagem do objeto de persistência Mapeado via JPA
├── repository/    # Interface que opera o banco para as queries customizadas e crud methods
├── service/       # Local responsável por injetar repositórios e ditar as especificações de negócio (Domain Rules)
└── exception/     # Ponto central para classes de erro customizadas, e o `GlobalExceptionHandler` manipulando falhas 
```

## ⚙️ Como Rodar a Aplicação

Para executar o sistema na sua máquina, siga os passos abaixo:

### Pré-requisitos
* Java JDK 17 (ou superior) instalado e listado no `JAVA_HOME`.
* Ferramenta **Maven** na máquina (opcional, uma vez que o wrapper `mvnw` virá acoplado na estrutura base do projeto).
* Editor de sua preferência (IntelliJ, VS Code, Eclipse).

### Inicialização
1. Abra o terminal onde quer hospedar os arquivos e efetue o clone oficial do repositório remoto:
   ```bash
   git clone https://github.com/jrcosta/sctec-ia-devs-empreendimentos-api.git
   cd sctec-ia-devs-empreendimentos-api
   ```
2. Instale as bibliotecas base, resolva os _plugins_ e gere o empacotamento (`.jar`):
   ```bash
   ./mvnw clean install
   ```
3. Suba o servidor Spring:
   ```bash
   ./mvnw spring-boot:run
   ```
4. O servidor iniciará. A API responderá por default na porta 8080 (http://localhost:8080).
5. Como boa prática opcional, acesse a documentação dinâmica visual em seu navegador web (ex: `http://localhost:8080/swagger-ui.html`).


## 🌐 Exemplos de Endpoints

### 1️⃣ Criar um Novo Empreendimento
- **Operação:** `POST` `/api/v1/empreendimentos`
- **Corpo Esperado (JSON):**
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
- **Retorno de Sucesso:** Código HTTP `201 Created` retornando os dados armazenados + Id autogerado.

### 2️⃣ Listar os Empreendimentos
- **Operação:** `GET` `/api/v1/empreendimentos`
- **Retorno de Sucesso:** Código HTTP `200 OK` englobando a lista paginada com os objetos salvos na base da gestão e suas URLs relacionadas.

### 3️⃣ Recuperar e Atualizar Informações
- **Recuperar (GET):** Requisição para `/api/v1/empreendimentos/{id}` (Trazendo status: `200 OK`).
- **Atualizar (PUT):** Requisição para `/api/v1/empreendimentos/{id}` enviando um payload equivalente ao de criar recursos (Apenas campos editados/todos). Sucesso: `200 OK` (E o objeto resultante).

### 4️⃣ Excluir Registro
- **Operação:** `DELETE` `/api/v1/empreendimentos/{id}`
- **Retorno de Sucesso:** A remoção trará o HTTP `204 No Content` provando total limpeza local do Id desejado. (Se o Id for inexistente trará um `404 Not Found`).


## 🎥 Link do Vídeo Pitch

Segue o link do vídeo final (de 3 minutos) sobre a apresentação oficial entregue:

> [Assista ao vídeo pitch do Desafio SCTEC Backend (YouTube/Vimeo)]()

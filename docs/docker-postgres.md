# Ambiente Docker, PostgreSQL e Seed

Esta documentação detalha a arquitetura do ambiente de banco de dados e as razões pelas quais o projeto transitou da dependência exclusiva do **H2 in-memory** para incluir o suporte local via **PostgreSQL** orquestrado pelo Docker.

## 1. Contexto e Motivação

O H2 in-memory é excelente para desenvolvimento fluído porque limpa os dados sempre que a API (Spring Boot) reinicia sem comprometer o sistema operacional host. Contudo, em cenários produtivos e testes de integração fidedignos, nós precisamos simular o comportamento de um banco de dados relacional oficial.

Para atender o nível de Senioridade deste repositório na **Etapa 6**, introduzimos o **PostgreSQL 15** atrelado diretamente à estrutura nativa dos contêineres e um Script de Injeção de Dados (Seed), eliminando atritos da máquina física do desenvolvedor.

## 2. Abordagem de Containerização

Foram criados os seguintes pilares através do Docker:

1. **Dockerfile Multi-Stage**: 
   - A fase de **Build** utiliza uma imagem robusta com o ecossistema Maven embutido (`eclipse-temurin:17-jdk-alpine`) para ler o código-fonte, resolver dependências off-line no `.mvn` e exportar o pacote compilado desconsiderando os testes locais.
   - A fase de **Run** seleciona um SO leve voltado à otimização e isolamento `eclipse-temurin:17-jre-alpine`, arrasta o arquivo `.jar` gerado na etapa anterior e injeta o `ENTRYPOINT` executando o serviço na porta 8080. 
   - Essa manobra **dispensa** o Dev de abrir sua IDE e instalar o Maven globalmente.

2. **Docker Compose**: 
   - Arquivo utilitário `docker-compose.yml` que sobe duas instâncias vinculadas pela ponte interna de rede: a "api" e o "db" (Postgres 15).
   - Ele cria volumes interativos garantindo a sustentação e blindagem persistente (`pgdata`) do que for testado.

## 3. Parametrização Flexível (Fallback)

Para que a implementação do Docker **não quebrasse ou travasse** a experiência padrão do Spring Boot na máquina (rodando no famoso botão de `Play` da IDE), adotamos um conceito flexível no `src/main/resources/application.properties`:

```properties
spring.datasource.url=${DB_URL:jdbc:h2:mem:testdb}
spring.datasource.username=${DB_USERNAME:sa}
spring.datasource.password=${DB_PASSWORD:}
spring.datasource.driver-class-name=${DB_DRIVER:org.h2.Driver}
spring.jpa.database-platform=${DB_DIALECT:org.hibernate.dialect.H2Dialect}
```

A diretiva `${VAR:DEFAULT}` informa ao Spring:
- **No Docker**: Injetaremos as URL e Senhas reais do PostgreSQL criadas no "services" do Compose via variáveis de ambiente. A aplicação usa e modela as tabelas no Postgres, descartando o H2.
- **No PC do Dev**: Como não há docker rodando, o Spring lê o `DEFAULT` contido depois do dois-pontos e injeta sozinho novamente o H2 Database na memória RAM de desenvolvimento contínua. Sem estresses.

## 4. Flyway Migrations e Seeding

Abandonamos a perigosa propriedade geradora nativa do Hibernate (`ddl-auto=update`) e o script solto de `data.sql`. A arquitetura atual usufrui de **Migrations Formais** orquestradas pelo **Flyway**, provendo versionamento profissional na pasta `src/main/resources/db/migration/`:

1. `V1__create_table_empreendimentos.sql`: Prepara e constrói a tabela com as primárias, índices e campos pre-definidos no banco Postgres ou H2 em caso de ramificação local.
2. `V2__insert_seed_empreendimentos.sql`: Executado após a criação, preenche imediatamente as linhas das tabelas com 3 registros fantasiados propícios para debug rápido na UI.

> **Atenção**: Esta estratégia de V1 e V2 executa infalível independente do repositório final de banco de dados e impede sobrescritas de metadados em re-starts frequentes. 

## 5. Como Executar?

Garanta o Docker Engine inicializado em segundo plano no seu SO, entre na raiz pelo seu console preferido, e insira os comandos:

```bash
# Apague projetos residuais da porta 8080, e construa a imagem do zero, subindo as instâncias sem travar o console (-d):
docker compose up --build -d

# Caso queira validar os logs em tempo real que saem do Spring interagindo com o banco:
docker compose logs -f api

# Para encerrar o expediente sem deletar o banco:
docker compose stop

# Para destruir tudo com força bruta após os testes:
docker compose down -v
```

## 6. Validando os Dados no Navegador

Como o *Seed* injeta 3 empreendimentos assim que o banco de dados liga na arquitetura, você não precisa fazer nenhum POST manual ou usar ferramentas pesadas como Insomnia para checar se a API está de pé e conectada ao Postgres com sucesso.

Basta abrir o seu navegador preferido (Chrome, Edge, Firefox) e acessar (ou clicar) nesta URL:

👉 **[http://localhost:8080/api/v1/empreendimentos](http://localhost:8080/api/v1/empreendimentos)**

A API responderá com os dados em JSON limpo listando todos os empreendimentos paginados recém-criados.

## Descrição

Este pull request implementa os testes automatizados garantindo qualidade na cobertura do CRUD dos empreendimentos, além de corrigir as dependências do `pom.xml` para a suíte do Spring Boot.

- **Cobertura via MockMvc e Mockito**: Adicionado 19 teses cobrindo fluxos de sucesso e fracasso do Service (`EmpreendimentoServiceImplTest`) e dos endpoints HTTP via Controller (`EmpreendimentoControllerTest`).
- **Validação Isolada do Model/DTO**: Foram injetados simulações de payload nulo para acionar validadores como `@NotBlank` garantindo proteção robusta nos DTOs através de Asserts no MockMvc e checagens JSON.
- **Transação Resiliente**: O `GlobalExceptionHandler` configurado na etapa anterior foi testado integralmente forçando `ResourceNotFoundException`.
- **Manutenção de Arquitetura**: Atualização direta do `pom.xml`, englobando e corrigindo injeções de Jackson e Test Containers limitados ao pacote `spring-boot-starter-test`, eliminando versões falsas no WebMvc Test dependencie tree.

**Relaciona a:** Etapa 5 do Desafio IA para Devs (SCTEC).

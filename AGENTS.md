# DIRETRIZES PARA AGENTES DE IA (AGENTS.md)

Este arquivo define as regras fundamentais que todos os agentes de Inteligência Artificial devem seguir ao atuar neste repositório.

## Estrutura de Documentação do Desafio

Toda a base de conhecimento necessária para que o agente possa implementar as funcionalidades do projeto de forma aderente ao escopo do desafio encontra-se centralizada em formato Markdown dentro do diretório `docs/diretrizes-agentes-ia/`.

*   **`docs/diretrizes-agentes-ia/documento-oficial-desafio-sctec.md`**: Detalha todo o contexto, as solicitações completas, regras de entrega e o modo detalhado que o desafio será avaliado pela equipe do LAB365 / SCTEC.
*   **`docs/diretrizes-agentes-ia/plano-implementacao-desafio-backend-SCTEC.md`**: Determina os passos de execução em etapas, as entidades, arquitetura recomendada e o mapa dos endpoints necessários para aprovação.

## Regras Fundamentais

1. **Idioma Obrigatório**: Todas as respostas, interações e criação de documentos textuais ou comentários de código devem ser estritamente em **Português do Brasil**.
2. **Planejamento Passo a Passo**: Para qualquer implementação, o agente deve seguir uma abordagem incremental listada no Plano de Implementação (Etapa A a Etapa D). Executar etapa por etapa e sempre aguardar a confirmação do usuário antes de prosseguir com grandes blocos de código.
3. **Leitura de Contexto Obrigatória**: O agente **deve** realizar a leitura detalhada de ambos os arquivos de diretrizes presentes na pasta `docs/diretrizes-agentes-ia/` **antes** de propor ou injetar qualquer nova alteração arquitetural e de implementação nas camadas do Spring Boot.
4. **Boas Práticas e Qualidade**: O código gerado deve ser limpo, seguir os padrões da linguagem (Java 17+ e Spring Boot) e incluir o tratamento de erros e devidas validações. A entrega técnica tem peso de 40% na avaliação (conforme documento oficial).
5. **Formatação de Pull Requests**: A descrição dos Pull Requests gerados deve SEMPRE ser formatada corretamente com Markdown e quebras de linha adequadas. Recomenda-se utilizar um arquivo temporário (`--body-file`) ao invés de passar strings diretas via CLI no PowerShell para evitar problemas de escape de shell com `\n`.
6. **Uso Mandatório do Lombok**: Todas as implementações de Entidades (Entity) e DTOs devem delegar a verbosidade de construtores, métodos de acesso (`Getters`/`Setters`), além de comparadores visuais (`ToString`, `EqualsAndHashCode`) para as anotações geradas via biblioteca **Lombok** (`@Data`, `@Getter`, `@Setter`, `@NoArgsConstructor`, etc).

## Próximos Passos
Sempre inicie revisando as diretrizes descritas acima para ter a convicção do plano traçado. Tendo feito isso, confirme o entendimento com o usuário antes de alterar códigos-fonte no domínio da aplicação.

## Descrição

Implementa a modelagem principal do domínio de negócios requerida pelo Desafio SCTEC.

Dependências implementadas:
- Entidade `Empreendimento` (JPA Entity);
- Enum `Segmento` com os ecossistemas contemplados.
- Enum `Status` (ATIVO/INATIVO).
- Interface `EmpreendimentoRepository` extendendo do framework padrão Data JPA.

**Nota (Refatoração)**: O projeto agora inclui a biblioteca **Lombok** declarada no `pom.xml`. Códigos repetitivos (`Getters`, `Setters`, `Constructors`) da `Empreendimento.java` (e futuras entidades/DTOs) foram delegados às anotações `@Data`, `@NoArgsConstructor` e `@AllArgsConstructor`. Os arquivos `README.md` e `AGENTS.md` foram atualizados refletindo as novas regras técnicas e documentacionais.

**Relaciona a:** Etapa 3 do Desafio IA para Devs (SCTEC).

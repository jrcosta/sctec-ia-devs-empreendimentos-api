# IA PARA DEVS

## Descritivo do desafio prático de software

## SUMÁRIO

### [1. OBSERVAÇÃO](#1-observação)
### [2. CONTEXTO](#2-contexto)
### [3. SOLICITAÇÃO](#3-solicitação)
### [4. ENTREGA](#4-entrega)
### [5. AVALIAÇÃO](#5-avaliação)

## 1. OBSERVAÇÃO

Este desafio prático integra o processo de seleção para a trilha IA para DEVs, conforme estabelecido no edital do programa SCTEC, voltado a pessoas desenvolvedoras que já atuam com programação e que desejam aprender a utilizar recursos de Inteligência Artificial e automações para aprimorar sua produtividade e a qualidade do código ao longo de sua jornada profissional.

A realização e a submissão deste desafio constituem etapas obrigatórias para fins de avaliação no referido processo seletivo, não se caracterizando como atividade formativa, curso ou certificação. O objetivo deste desafio é exclusivamente avaliar as competências técnicas do(a) candidato(a) no desenvolvimento de sistemas, de acordo com os critérios definidos no edital.

A submissão da solução implica a ciência e a concordância do(a) candidato(a) com as regras estabelecidas neste desafio e no edital ao qual está vinculado, incluindo critérios de avaliação, prazos e condições de participação. Considerando que o número de vagas é limitado, a realização do desafio não garante a aprovação no processo seletivo. Para informações completas sobre a trilha IA para DEVs, o programa SCTEC, os critérios de seleção e o cronograma, o(a) candidato(a) deverá consultar o edital e o site oficial do programa.

## 2. CONTEXTO

Santa Catarina destaca-se nacionalmente pelo seu ambiente favorável ao empreendedorismo, com forte presença de micro e pequenas empresas, startups e iniciativas voltadas à inovação e ao desenvolvimento econômico.

Com o objetivo de organizar informações relacionadas a empreendimentos e seus responsáveis no estado, deseja-se construir uma solução computacional simples que permita o cadastro, a consulta e a manutenção desses dados de forma estruturada.

A aplicação proposta deverá servir como um protótipo de sistema para apoiar a organização de informações sobre empreendimentos catarinenses, podendo futuramente ser integrada a outras soluções.

## 3. SOLICITAÇÃO

O(a) candidato(a) deverá desenvolver uma aplicação do tipo **CRUD (Create, Read, Update, Delete)** relacionada ao tema **empreendedorismo em Santa Catarina**, escolhendo apenas **uma abordagem**, conforme sua maior afinidade técnica: desenvolvimento de interface (front-end) ou desenvolvimento de serviço (back-end).

Na opção de desenvolvimento de interface (front-end), a aplicação deverá permitir a interação do usuário por meio de uma interface gráfica, podendo ser implementada como uma aplicação web, mobile ou desktop. Na opção de desenvolvimento de serviço (back-end), a aplicação deverá disponibilizar um serviço responsável pelo gerenciamento dos dados, podendo ser implementada como uma API REST, GraphQL ou outro tipo de serviço equivalente.

Independentemente da abordagem escolhida, o sistema deverá permitir o gerenciamento de informações sobre empreendimentos, contemplando, no mínimo, os seguintes campos:
- Nome do empreendimento;
- Nome do(a) empreendedor(a) responsável;
- Município de Santa Catarina;
- Segmento de atuação:
  - Tecnologia;
  - Comércio;
  - Indústria;
  - Serviços;
  - Agronegócio.
- E-mail ou meio de contato;
- Status (ativo ou inativo).

Poderão ser adicionados outros campos a critério do(a) candidato(a), desde que relacionados ao contexto proposto.

O sistema deverá permitir, de forma funcional, o cadastro de novos empreendimentos, a listagem dos registros existentes, a edição das informações cadastradas e a remoção de registros.

A linguagem de programação, o framework, a arquitetura e o mecanismo de persistência de dados são de livre escolha do(a) candidato(a). A solução desenvolvida deverá ser autoral, funcional e executável conforme as instruções fornecidas na documentação criada.

É obrigatória a elaboração de documentação por meio de um arquivo `README.md`, no qual deverão constar, no mínimo, a descrição da solução desenvolvida, as tecnologias utilizadas, a estrutura geral do projeto e as instruções necessárias para sua execução. A documentação será parte integrante da avaliação.

Não é exigida a implementação de autenticação de usuários, nem a construção de uma interface visual complexa. Também não é exigido o uso de técnicas de Inteligência Artificial neste desafio.

## 4. ENTREGA

A entrega deverá ser realizada exclusivamente por meio do envio de um único link para um **repositório público no GitHub**, acessível sem autenticação.

O repositório deverá conter todo o código-fonte da aplicação e, obrigatoriamente, um arquivo `README.md` de documentação.

Na documentação `README.md` deverá constar, no mínimo:
- A descrição da solução desenvolvida;
- As tecnologias utilizadas;
- A estrutura geral do projeto;
- As instruções necessárias para sua execução;
- Um link para o vídeo pitch, que deverá estar referenciado diretamente no `README.md`.

O vídeo pitch deverá estar hospedado em plataforma de livre acesso (como YouTube ou similar) e possuir **duração máxima de 3 (três) minutos**. Nesse vídeo, o(a) candidato(a) deverá apresentar a solução desenvolvida, explicar suas principais funcionalidades, demonstrar brevemente o funcionamento do sistema (quando aplicável) e comentar as principais decisões técnicas adotadas.

Somente serão aceitas submissões realizadas por meio de link de repositório público no GitHub contendo o arquivo `README.md` com o respectivo link para o vídeo pitch.

## 5. AVALIAÇÃO

A avaliação do desafio será realizada pelo SENAI/SC LAB365, com base na análise do repositório submetido, considerando a qualidade técnica da solução, sua aderência ao escopo proposto e a clareza na documentação apresentada.

A nota final será atribuída em uma escala de 0 (zero) a 100 (cem) pontos, conforme os critérios descritos a seguir:

### Critérios de Avaliação do Desafio de Software

| Nº | Critério / Peso | 50 pontos | 70 pontos | 100 pontos |
|:---|:---|:---|:---|:---|
| 1 | **Quantidade mínima de caracteres na documentação do projeto (README.md)**<br>**Peso:** 10% | A documentação do projeto contém menos de 600 caracteres. | A documentação do projeto contém entre 600 e 1200 caracteres. | A documentação do projeto contém mais de 1200 caracteres. |
| 2 | **Versionamento com branches**<br>**Peso:** 10% | O repositório possui apenas 1 branch. | O repositório possui entre 2 e 3 branches. | O repositório possui mais de 3 branches. |
| 3 | **Organização com commits**<br>**Peso:** 10% | O repositório possui apenas 4 commits distintos e relevantes. | O repositório possui entre 5 e 8 commits distintos e relevantes. | O repositório possui mais de 8 commits distintos e relevantes. |
| 4 | **Aderência ao escopo do desafio**<br>**Peso:** 30% | A solução apresentada atende parcialmente ao escopo proposto, implementando apenas parte das funcionalidades obrigatórias ou desviando do contexto definido no desafio. | A solução apresentada atende ao escopo principal do desafio, implementando as funcionalidades obrigatórias, com pequenas limitações ou simplificações. | A solução apresentada atende integralmente ao escopo do desafio, implementando corretamente as funcionalidades obrigatórias e respeitando o contexto e os requisitos definidos. |
| 5 | **Qualidade do código**<br>**Peso:** 40% | Código com organização limitada, baixa legibilidade ou inconsistências relevantes, apresentando pouca aderência às boas práticas de programação. | Código funcional, organizado e legível, utilizando adequadamente a linguagem escolhida, ainda que com pontos de melhoria. | Código bem estruturado, legível, consistente e alinhado às boas práticas de programação, com separação adequada de responsabilidades e coerência com a descrição apresentada no README.md. |
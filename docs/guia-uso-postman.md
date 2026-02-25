# Testando a API de Empreendimentos com Postman

Este projeto inclui uma coleção pronta do Postman com endpoints configurados para facilitar os testes manuais da API de Empreendimentos.

## Como Importar

1. Baixe e instale o [Postman](https://www.postman.com/downloads/), ou acesse via web.
2. Com o Postman aberto, clique em **Import** (canto superior esquerdo) ou utilize o atalho `Ctrl+O`.
3. Arraste e solte o arquivo `docs/sctec-ia-devs-empreendimentos-api.postman_collection.json` na área indicada ou navegue pelos arquivos locais de seu computador para selecioná-lo.
4. O Postman automaticamente importará a coleção ("SCTEC Empreendimentos API") contendo os 5 endpoints (Criar, Listar, Buscar, Atualizar e Deletar).

## Estrutura da Coleção

A coleção de testes possui uma variável embutida chamada `base_url`. Por padrão, ela vem configurada para acessar a aplicação sendo executada na máquina local da seguinte forma:

```
http://localhost:8080
```

Se precisar alterar o host ou a porta devido a configurações (por exemplo servidor em Nuvem/Docker), basta clicar com o botão direito sobre a documentação/nome da coleção _SCTEC Empreendimentos API_, ir em **Edit/Variables** e modificar o "Current Value" dessa variável `base_url`.

## Endpoints Disponíveis

* **Criar Empreendimento (`POST /api/v1/empreendimentos`)**:
  * Inclui um Payload de exemplo (JSON body).
* **Listar Empreendimentos (`GET /api/v1/empreendimentos`)**:
  * Já configurada com Parâmetros da Query paramétricos como `page=0` e `size=10`.
* **Buscar Empreendimento por ID (`GET /api/v1/empreendimentos/1`)**
* **Atualizar Empreendimento (`PUT /api/v1/empreendimentos/1`)**:
  * Modifique o ID na URL e inclua o corpo atualizado da entidade que deseja fazer a mutação.
* **Deletar Empreendimento (`DELETE /api/v1/empreendimentos/1`)**:
  * Ao ser concluído com sucesso (Status 204), esta chamada não possui response body.

Aproveite para validar os diferentes códigos de retorno de cada requisição.

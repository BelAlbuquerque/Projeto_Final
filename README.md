# desafio_final

## Desafio agregador III

Esse projeto foi desenvolvido por:

- [Andrea](https://github.com/andherreraML)

- [Bel](https://github.com/BelAlbuquerque)

- [Karina](https://github.com/KarinaLimaMeli)

- [Letícia](https://github.com/lecastroMELI)

- [Nana](https://github.com/InajaraPereira)

- [Sandi](https://github.com/sandiouriquemeli)

### Sobre:

Nesse projeto, aplicamos os conteudos que aprendemos até este momento no BootCamp de Java.
Trata-se de uma aplicação desenvolvida de forma a implementar uma API REST.

### Requisito pessoal:

Como melhoria para a aplicação, escolhi implementar uma camada de proteção à aplicação utilizando JWT.
Assim, alguns acessoas a determinadas rotas ficam restritos a determinado tipo de usuário.
Também, o reconhecimento do usuário via token, facilitando a utilização e tirando a necessidade do usuário saber a pk dele no banco de dados
e evitando que o usuário acesse dados e rotas que não deveria.

com isso, implementei 4 rotas:

-rota para cadastrar um novo usuário;

-rota para login (onde é gerado um token do tipo Bearer);

-rota para que um vendedor("seller") consiga ver a lista de clientes que já compraram seus produtos;

-rota para que um consumidor("buyer") consiga ver a lista de vendedores dos quais já comprou produtos;

Além disso, foi adicinado restriçoes através do token a determinadas rotas:

- #### Cadastrar um novo usuário - **permitido a todos os tipos de usuários**;

  - (POST) http://127.0.0.1:8080/user/registry

- #### Fazer login para conseguir o token - **permitido a todos os tipos de usuários**;

  - (POST) http://127.0.0.1:8080/login

- #### Listar todos os clientes(BUYER) que já compraram os produtos do usuário(SELLER) - **somente o vendedor (SELLER) que está logado**;

  - (GET) http://127.0.0.1:8080/user/buyers

- #### Listar todos os vendedores (SELLER) um usuário (BUYER) já comprou - **somente o cliente/comprador (BUYER) que está logado**;

  - (GET) http://127.0.0.1:8080/user/buyers

- #### Cadastrar um lote de produtos - **somente um representante(AGENT)**;

  - (POST) http://127.0.0.1:8080/api/v1/fresh-products/inboundorder/

- #### Atualizar um lote de produtos - **somente um representante(AGENT)**;

  - (PUT) http://127.0.0.1:8080/api/v1/fresh-products/inboundorder/

- #### Consultar um produto - **permitido a todos os tipos de usuários**;

  - (GET) http://127.0.0.1:8080/api/v2/adsenses/warehouse/{adsenseId_1}

- #### Listar produtos por categoria - **permitido a todos os tipos de usuários**;
  - <sup>Onde *querytype* pode ser FRESH / FROZEN / REFRIGERATED</sup>

  - (GET) http://127.0.0.1:8080/api/v2/adsenses/list?querytype=FRESH

- #### Listar todos os produtos - **permitido a todos os tipos de usuários**;

  - (GET) http://127.0.0.1:8080/api/v2/adsenses

- #### Adicionar o produto a um carrinho de compras - **somente um client/comprador(BUYER)**;

  - (POST) http://127.0.0.1:8080/api/v2/fresh-products/orders

- #### Mostrar os produtos no pedido - **somente um client/comprador(BUYER)**;
  
  - (GET) http://127.0.0.1:8080/api/v2/fresh-products/orders/{purchaseOrderId}

- #### Modificar o status do pedido - **somente um client/comprador(BUYER)**;

  - (POST) http://127.0.0.1:8080/api/v2/fresh-products/orders/?purchaseOrderId={purchaseOrderId}

- #### Listar  todos os produtos - **permitido a todos os tipos de usuários**;

  - (GET) http://127.0.0.1:8080/api/v2/fresh-products

- #### Listar todos os produtos por categoria - **permitido a todos os tipos de usuários**;
  - <sup>Onde *querytype* pode ser FRESH / FROZEN / REFRIGERATED</sup>

  - (GET) http://127.0.0.1:8080/api/v2/fresh-products/list?querytype=FROZEN

- #### Listar todos os lotes de um determinado produto - **somente um representante ou um vendedor (AGENT, SELLER)**;
  - <sup>Onde *productId* é o id do produto que deseja buscar</sup>
  - <sup>Onde *s* é o ?????????? que deseja ordenar</sup>

  - (GET) http://127.0.0.1:8080/api/v2/fresh-products/sortlist?productId=1&s=L

- #### Listar  todos anuncios - **permitido a todos os tipos de usuários**;

  - (GET) http://127.0.0.1:8080/api/v2/adsenses

- #### Listar  todos anuncios por categoria - **permitido a todos os tipos de usuários**;
  - <sup>Onde *querytype* pode ser FRESH / FROZEN / REFRIGERATED</sup>

  - (GET) http://127.0.0.1:8080/api/v2/adsenses/list?querytype=FROZEN

- #### Busca por um anuncio específico em um armazem - **permitido a todos os tipos de usuários**;

  - (GET) http://127.0.0.1:8080/api/v2/adsenses/warehouse/{adsenseId}

- #### Listar todos os lotes de um anuncio - **somente um representante ou um vendedor (AGENT, SELLER)**;

  - (GET) http://127.0.0.1:8080/batch/{adsenseId}

- #### Listar os lotes de um setor em um armazém ordenados pela data de vencimento - **somente um representante ou um vendedor (AGENT, SELLER)**;
  - <sup>Onde *sectionId* é o id da section que deseja buscar</sup>
  - <sup>Onde *numberOfDays* é o intervalo de dias, a partir do dia da busca, que um produto pode vencer</sup>

  - (GET) http://127.0.0.1:8080/batch/due-date?sectionId=1&numberOfDays=25

- #### Listar os lotes dentro do prazo de validade solicitado que pertece a uma determinada categoria de produto, podendo 
  ser ordenada pela quantidade de forma crescente ou decrescente - **somente um representante ou um vendedor (AGENT, SELLER)**;
  - <sup>Onde *category* pode ser FRESH / FROZEN / REFRIGERATED</sup>
  - <sup>Onde *order* pose ser ASC (crescente) / DESC (descrescente)</sup>
  - <sup>Onde *numberOfDays* é o intervalo de dias, a partir do dia da busca, que um produto pode vencer</sup>

  - (GET) http://127.0.0.1:8080/batch/due-date/list?numberOfDays=25&category=FRESH&order=ASC

**Como a nossa equipe de desenvolvedores é muito preocupada com a qualidade do nosso serviço,
desenvolvemos testes unitários, garantindo que nossa aplicação funciona, é escalável e segura;**

A documentação das rotas está neste [arquivo](Desafio%203%20-%20Projeto%20Integrador.postman_collection.json)


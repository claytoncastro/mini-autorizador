![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/claytoncastro/mini-autorizador/maven.yml?label=Build)

# Mini autorizador

API de autorização de transações que realiza uma série de verificações e análises. Essas também são conhecidas como *regras de autorização*.

O autorizador toma uma decisão, aprovando ou não a transação seguindo os seguintes princípios:
* se aprovada, o valor da transação é debitado do saldo disponível do benefício, e informamos que tudo ocorreu bem.
* senão, apenas informamos o que impede a transação de ser feita e o processo se encerra.

Esta é uma aplicação Spring Boot com interface totalmente REST que permite:

* a criação de cartões (todo cartão deverá ser criado com um saldo inicial de R$500,00);
* a obtenção de saldo do cartão;
* a autorização de transações realizadas usando os cartões previamente criados como meio de pagamento.

## Regras de autorização

Uma transação pode ser autorizada se:
* o cartão existir
* a senha do cartão for a correta
* o cartão possuir saldo disponível

Caso uma dessas regras não seja atendida, a transação não será autorizada.

## Demais instruções

#### Cenários possíveis
Os seguintes cenários são possíveis, nesta ordem:

* Criação de um cartão;
* Verificação do saldo do cartão recém-criado;
* Realização de diversas transações, verificando-se o saldo em seguida, até que o sistema retorne informação de saldo insuficiente;
* Realização de uma transação com senha inválida;
* Realização de uma transação com cartão inexistente.

#### Pré requisitos para subir a aplicação

* Softwares
  * Java 11
  * Maven 3.8.1
  * Docker
* Com docker rodando, navegar até a pasta *docker* no *root* dessa aplicação e rodar o comando abaixo:
  ```
  docker-compose up
  ```
  
## Contratos dos serviços

#### Criar uma cartão novo
---
* **Criar novo cartão**
   ```
   Method: POST
   URL: http://localhost:8080/cartoes
   Body (json):
   {
      "numeroCartao": "6549873025634501",
      "senha": "1234"
   }
   ```
#### Possíveis respostas

* **Criação com sucesso**
   ```
   Status Code: 201
   Body (json):
   {
      "senha": "1234",
      "numeroCartao": "6549873025634501"
   } 
   ```

* **Cartão já exista**
   ```
   {
      "title": "Recurso já existe!!",
      "status": 422,
      "detail": "Cartão com número '87652453112355' já existe!",
      "timestamp": 1673617944174,
      "developerMessage": "ResourceAlreadyExistException"
   }
   ```

* **Caso seja passado no body algum campo vazio ou nulo**
   ```
   Status Code: 422
   Body (json):
   {
      "title": "Erro de validação de campo",
      "status": 400,
      "detail": "Erro de validação de campo",
      "timestamp": 1673544053244,
      "developerMessage": "MethodArgumentNotValidException",
      "field": "senha, numeroCartao",
      "fieldMessage": "Campo 'senha' não pode ser branco ou nulo, Campo 'numeroCartao' não pode ser branco ou nulo"
   }
   ```

#### Saldo do Cartão
---
* **Obter saldo do cartão**
   ```
   Method: GET
   URL: http://localhost:8080/cartoes/{numeroCartao} , onde {numeroCartao} é o número do cartão que se deseja consultar
   ```

#### Possíveis respostas

* **Obtenção com sucesso**
   ```
   Status Code: 200
   Body (json):
   {
      "numeroCartao": "87652453112355",
      "saldo": 479.5
   }
   ```
* **Caso o cartão não exista**
   ```
   Status Code: 404
   Body (json):
   {
      "title": "Recurso não existe!!",
      "status": 404,
      "detail": "Cartão com número '876524531123551' não existe!",
      "timestamp": 1673618654730,
      "developerMessage": "ResourceNotFoundException"
   }
   ```

#### Realizar uma Transação
---
* **Realizar transação**
   ```
   Method: POST
   URL: http://localhost:8080/transacoes
   Body (json):
   {
      "numeroCartao": "6549873025634501",
      "senhaCartao": "1234",
      "valor": 10.00
   }
   ```

#### Possíveis respostas

* **Transação realizada com sucesso**
   ```
   Status Code: 201
   Body (json):
   {
      "numeroCartao": "87652453112355",
      "saldo": 459.0
   }
   ```
* **Caso seja passado no body algum campo vazio ou nulo**
   ```
   Status Code: 422
   Body (json):
   {
      "title": "Erro de validação de campo",
      "status": 400,
      "detail": "Erro de validação de campo",
      "timestamp": 1673544053244,
      "developerMessage": "MethodArgumentNotValidException",
      "field": "senha, numeroCartao",
      "fieldMessage": "Campo 'senha' não pode ser branco ou nulo, Campo 'numeroCartao' não pode ser branco ou nulo"
   }
   ```

* **Caso o saldo seja insuficiente**
   ```
   Status Code: 422
   Body (json):
   {
      "title": "Recurso não pode ser processado!!",
      "status": 422,
      "detail": "Saldo insuficiente. Saldo atual '459.0', valor solicitado '500.0'",
      "timestamp": 1673619604547,
      "developerMessage": "ResourceUnprocessableException"
   }
   ```

* **Caso a senha seja inválida**
   ```
   Status Code: 422
   Body (json):
   {
      "title": "Recurso não pode ser processado!!",
      "status": 422,
      "detail": "Senha inválida!",
      "timestamp": 1673619645398,
      "developerMessage": "ResourceUnprocessableException"
   }
   ```

* **Caso o cartão seja inexistente**
   ```
   Status Code: 404
   Body (json):
   {
      "title": "Recurso não existe!!",
      "status": 404,
      "detail": "Cartão com número '8765245311235511' não existe!",
      "timestamp": 1673619676581,
      "developerMessage": "ResourceNotFoundException"
   }
   ```

### Documentação
#### Swagger
A documentação das requisições podem ser acessadas através do *Swagger*, conforme link:

* **[Documentação Swagger](http://localhost:8080/swagger-ui.html)**

#### Postman Collection
Você também pode baixar a *collection* do *Postman*, importar e testar via Postman

* **[Postman Collection](https://github.com/claytoncastro/mini-autorizador/blob/main/src/main/resources/collection/mini-autorizador.postman_collection.json)**

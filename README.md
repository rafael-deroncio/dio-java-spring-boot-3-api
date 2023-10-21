# Santander Dev Week 2023 Java API

RESTful API da Santander Dev Week 2023 construída em Java 21 com Spring Boot 3.

## Principais Tecnologias
 - **Java 21**: Utilizaremos a versão LTS mais recente do Java para tirar vantagem das últimas inovações que essa linguagem robusta e amplamente utilizada oferece;
 - **Spring Boot 3**: Trabalharemos com a mais nova versão do Spring Boot, que maximiza a produtividade do desenvolvedor por meio de sua poderosa premissa de autoconfiguração;
 - **Spring Data JPA**: Exploraremos como essa ferramenta pode simplificar nossa camada de acesso aos dados, facilitando a integração com bancos de dados SQL;
 - **OpenAPI (Swagger)**: Vamos criar uma documentação de API eficaz e fácil de entender usando a OpenAPI (Swagger), perfeitamente alinhada com a alta produtividade que o Spring Boot oferece;
 - **Railway**: Facilita o deploy e monitoramento de nossas soluções na nuvem, além de oferecer diversos bancos de dados como serviço e pipelines de CI/CD.
 - **MapperModel**: Utilizaremos a biblioteca MapperModel para mapear objetos entre diferentes camadas da aplicação de forma eficiente;
 - **JUnit 5**: Faremos uso do JUnit 5 para escrever testes unitários robustos que garantam a qualidade do nosso código;
 - **Mockito**: Com o Mockito, poderemos criar mocks de objetos e simular comportamentos, facilitando a escrita de testes de unidade mais abrangentes e eficazes.

## Abstração do domínio 
[Figma](https://www.figma.com/file/cdryxczFtFlledgPmqmXr1/Aplicativo-Banco-Community?type=design&mode=design&t=zSFbowLuxkJkvYhk-1)
O Figma foi utilizado para a abstração do domínio desta API, sendo útil na análise e projeto da solução.

## Domínio da API - Contratos

```mermaid
classDiagram
  class User {
    -String name
    -Account account
    -CreditCard card
    -Investment investment
    -Pix pix
  }


  class Account {
    -String number
    -String agency
    -Number balance
    -Number limit
    -AccountTransaction[] transactions
  }

  class Investment {
    -Number totalInvested
    -Number incomesRescueDisposable
    -Number[] incomes
    -Number year
    -Pix pix
  }
  
  class CreditCard {
    -String name
    -String number
    -String expires
    -Number limit
    -Number invoiceAmount
    -CreditCardTransaction[] transactions
  }

  class Pix{
    -PixDestiny destination
    -Number value
    }

  class PixDestiny{
    -String name
    -String key
    -String number
    -String agency
    }

  class AccountTransaction{
    -Timestamp datetime
    -String type
    -String local
    -Number value
    }

  class CreditCardTransaction{
    -Timestamp datetime
    -String type
    -String local
    -Number value
    }

  User  *--  Account
  User  *--  CreditCard
  User  *--  Pix
  User  *--  Investment
  Pix   *-- PixDestiny
  Account *-- AccountTransaction
  CreditCard *-- CreditCardTransaction
```

## Documentação da API

[Swagger](#app/swagger-ui.html)



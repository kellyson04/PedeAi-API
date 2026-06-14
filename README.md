# PedeAi API

API REST para um sistema de delivery, desenvolvida com Java 21, Spring Boot, PostgreSQL, Redis e autenticacao JWT.

O projeto foi criado para evoluir meus estudos em backend usando um dominio mais proximo de um produto real: usuarios, restaurantes, produtos, enderecos, carrinho, pedidos, controle de acesso por perfil e persistencia temporaria de carrinho com Redis.

Em relacao aos meus projetos anteriores, aqui aprofundei principalmente seguranca com JWT, regras de negocio por tipo de usuario, Redis para estado temporario, migrations com Flyway e validacoes mais proximas de um fluxo real de compra.

## Status do projeto

Em desenvolvimento.

Atualmente o projeto possui:

- API REST com arquitetura em camadas
- Autenticacao stateless com JWT
- Autorizacao por roles
- Login com controle de tentativas e bloqueio temporario de conta
- Cadastro e consulta de usuario autenticado
- Cadastro de restaurantes por donos de restaurante
- Abertura e fechamento de restaurante
- Cadastro de produtos por restaurante
- Consulta publica de cardapio
- Cadastro e listagem de enderecos do usuario
- Integracao com ViaCEP para consulta de endereco
- Carrinho persistido no Redis com TTL
- Criacao de pedidos
- Migrations de banco com Flyway
- Tratamento global de excecoes

## Funcionalidades

### Autenticacao e usuarios

- Cadastro de usuario
- Login com geracao de token JWT
- Bloqueio temporario de conta apos excesso de tentativas invalidas
- Consulta do perfil do usuario autenticado
- Controle de acesso por perfil:
  - `ADMIN`
  - `RESTAURANT_OWNER`
  - `CUSTOMER`

### Restaurantes

- Cadastro de restaurante
- Consulta do restaurante do usuario autenticado
- Abertura e fechamento de restaurante
- Consulta publica do cardapio
- Regras para impedir operacoes invalidas, como abrir restaurante ja aberto ou fechar restaurante ja fechado

### Produtos

- Cadastro de produto vinculado a um restaurante
- Validacao de produto existente
- Validacao de disponibilidade e estoque no carrinho

### Enderecos

- Cadastro de endereco do usuario autenticado
- Listagem dos enderecos do usuario
- Consulta de dados de endereco via ViaCEP

### Carrinho

- Adicao de produtos ao carrinho
- Carrinho salvo no Redis por usuario
- TTL configurado para expirar carrinhos antigos
- Soma de quantidade ao adicionar o mesmo produto novamente
- Validacao para impedir produtos de restaurantes diferentes no mesmo carrinho
- Validacao de estoque disponivel

### Pedidos

- Criacao de pedido
- Associacao entre cliente, restaurante e endereco
- Controle inicial de status do pedido

## Tecnologias utilizadas

- Java 21
- Spring Boot 4
- Spring MVC
- Spring Security
- Spring Data JPA
- Spring Data Redis
- PostgreSQL
- Redis
- Flyway
- Bean Validation
- Lombok
- Maven
- JWT
- WebClient

## Conceitos aplicados

- Arquitetura em camadas
- Controllers, Services, Repositories, DTOs e Mappers
- Autenticacao e autorizacao com Spring Security
- Autenticacao stateless com JWT
- Regras de autorizacao por perfil
- Validacao com `@Valid`
- Tratamento global de excecoes
- Relacionamentos JPA
- Transacoes com `@Transactional`
- Versionamento de banco com Flyway
- Consumo de API externa com WebClient
- Redis para dados temporarios
- Serializacao JSON tipada no Redis

## Principais rotas

| Recurso | Base path |
| --- | --- |
| Autenticacao | `/auth` |
| Usuarios | `/users` |
| Restaurantes | `/restaurants` |
| Produtos | `/products` |
| Enderecos | `/addresses` |
| Carrinho | `/cart` |
| Pedidos | `/orders` |

## Como executar localmente

### Pre-requisitos

- Java 21
- PostgreSQL
- Redis
- Maven Wrapper ja incluido no projeto

### Redis com Docker

```bash
docker run --name pedeai-redis -p 6379:6379 -d redis:7-alpine
```

### Variaveis de ambiente

Configure as variaveis usadas pelo `application.yaml`:

```text
DB_URL=jdbc:postgresql://localhost:5432/pedeai
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET_KEY=sua_chave_secreta
```

### Executar a aplicacao

No Windows:

```bash
./mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

A API sera iniciada em:

```text
http://localhost:9090
```

## Estrutura do projeto

```text
src/main/java/dev/kellyson/projeto/PedeAi/API
|-- address
|-- auth
|-- cart
|-- category
|-- config
|-- exception
|-- order
|-- product
|-- restaurant
`-- user
```

## Autor

Kellyson Lopes

- GitHub: [kellyson04](https://github.com/kellyson04)
- Email: kellysonlopes04@outlook.com

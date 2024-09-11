
# API Rest de Delivery

Esta é uma API Rest desenvolvida com Java e Spring Boot para um sistema de delivery. Ela permite o cadastro de usuários, login e, após o login, acesso a listagem de categorias e produtos, além de realizar pedidos.

## Como Utilizar a API com Postman

### 1. Cadastrar Usuário

- **Rota**: `POST http://localhost:8080/api/usuarios/cadastro`
- **Body (JSON)**:

```json
{
  "email": "usuario@email.com",
  "senha": "senha1234"
}
```

### 2. Login de Usuário

- **Rota**: `POST http://localhost:8080/api/usuarios/login`
- **Body (JSON)**:

```json
{
  "email": "usuario@email.com",
  "senha": "senha1234"
}
```

- **Resposta**: Um **token** será retornado. Ele é necessário para acessar as demais rotas protegidas.

### 3. Listar Categorias

- **Rota**: `GET http://localhost:8080/api/categorias`
- **Headers**:
  - **Token**: `<token obtido no login>`

### 4. Criar Categoria

- **Rota**: `POST http://localhost:8080/api/categorias`
- **Headers**:
  - **Token**: `<token obtido no login>`
- **Body (JSON)**:

```json
{
  "nome": "Bebidas"
}
```
### 5. Criar Produto
- **Rota:** POST `http://localhost:8080/api/produtos`
- **Headers:**
  - **Token:** <token obtido no login>
- **Body (JSON):**
```json
  {
  "nome": "Coca-Cola",
  "preco": 5.50,
  "categoria": {
    "id": 1
  }
}
```

### 6. Listar Produtos

- **Rota**: `GET http://localhost:8080/api/produtos`
- **Headers**:
  - **Token**: `<token obtido no login>`

### 7. Criar Pedido

- **Rota**: `POST http://localhost:8080/api/pedidos`
- **Headers**:
  - **Token**: `<token obtido no login>`
- **Body (JSON)**:

```json
{
  "produtos": [
    { "id": 1 }, 
    { "id": 2 }
  ],
  "endereco": "Rua Exemplo, 123"
}

```

## Observações

- **Token**: Lembre-se de usar o token obtido no login nas rotas protegidas (envie no header `Token`).
- **403 Forbidden**: Certifique-se de incluir o token correto para evitar esse erro.

## Configuração do Banco de Dados

A API utiliza o MySQL como banco de dados. Para configurá-lo, siga os passos abaixo:

1. Crie um banco de dados chamado `delivery` no MySQL.

   ```sql
   CREATE DATABASE delivery;
   ```

2. No arquivo `application.properties`, ajuste as informações de conexão do banco de dados:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/delivery
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
   ```

3. O Hibernate criará automaticamente as tabelas no banco de dados com base nas entidades da aplicação.

4. Certifique-se de que o MySQL esteja rodando e acessível.

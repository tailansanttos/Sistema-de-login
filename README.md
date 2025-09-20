# Sistema de Autenticação e Autorização

Este é um sistema de autenticação e autorização, desenvolvido com Java (Spring Boot) no backend e Angular no frontend. O objetivo principal é fornecer uma base segura para gerenciar usuários, utilizando JSON Web Tokens (JWT) para proteger as rotas da API.
<br>

## 🚀 Tecnologias Utilizadas

### Backend (Java)
- Spring Boot, Spring Security,JWT (JSON Web Token), JPA & Hibernate(Para persistência de dados em banco de dados relacional)PostgreSQL, Maven

### Frontend (Angular)
- Angular, TypeScript

## ⚙️ Como Executar o Projeto

Para rodar a aplicação em seu ambiente de desenvolvimento, siga estes passos. É necessário ter o **Maven** e o **PostgreSQL** instalados em sua máquina.

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/tailansanttos/Sistema-de-login.git](https://github.com/tailansanttos/Sistema-de-login.git)
    ```

2.  **Configurar o Banco de Dados:**
    - Crie um banco de dados PostgreSQL com o nome `sistema_login`.
    - Ajuste o seu `application.properties` com as credenciais corretas do seu banco de dados local, secret do jwt e issuer.
    - Exemplo de `application.properties`:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/sistema_login
        spring.datasource.username=seu_usuario
        spring.datasource.password=sua_senha
        
        SECRET_KEY=${JWT_KEY}
        JWT_ISSUER=${ISSUER_JWT}
        ```

3.  **Compilar e Iniciar a Aplicação:**
    - Abra o terminal na raiz do projeto.
    - Execute o comando Maven para compilar, empacotar e rodar a aplicação.
    ```bash
  
    mvn spring-boot:run
    ```
    - Aguarde até que o log da sua aplicação Spring Boot mostre "Tomcat started on port 8080".

4.  **Acessar a Aplicação:**
    - **Backend (API):** A API estará disponível em `http://localhost:8080`.
    - **Documentação (Swagger UI):** A documentação da API pode ser acessada em `http://localhost:8080/swagger-ui.html`.

<br>

## 🔒 Endpoints de Autenticação

-   **`POST /auth/register`**: Registra um novo usuário e cliente.
    -   Requisição: `{"email": "...", "password": "...", "nome": "...", ...}`
    -   Respostas: `201 Created` ou `409 Conflict` (se o usuário já existir).
-   **`POST /auth/login`**: Realiza o login e retorna um JWT.
    -   Requisição: `{"email": "...", "password": "..."}`
    -   Respostas: `200 OK` com o token no corpo ou `401 Unauthorized` (credenciais inválidas).

<br>


## 🤝 Contribuições

Contribuições, sugestões e melhorias são sempre bem-vindas!

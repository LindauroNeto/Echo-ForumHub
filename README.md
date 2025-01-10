# ForumHub - Compartilhando conhecimento 💬📢
Sistema de fórum para a criação de tópicos para sanar dúvidas de cursos e envio de respostas.

## 🔨 Funcionalidades
No momento o sistema possui funcionalidades para as partes de **Tópicos** e **Usuários**.

### Tópicos
1. **Cadastro de um novo tópico**: Ao preencher na requisição os campos: `titulo`, `mensagem`, `autor` e `curso`. A requisição é enviada ao sistema e salva no banco de dados.
2. **Listagem dos tópicos**: Listagem única (informando o id) ou de todos os tópicos criados, em forma paginada.
3. **Atualização de informações**: Ao informar no caminho da url o id do tópico, é possível alterar as informações do tópico, ao preencher os campos `titulo` e `mensagem` no corpo da requisição aos novos valores atribuídos.
4. **Exclusão do tópico**: Ao informar no caminho da url o id do tópico, ele é excluído de forma lógica do sistema (sendo necessário realizar um DELETE no banco de dados para uma exclusão definitiva do tópico).

### Usuários
1. **Cadastro**: Realização de cadastro do usuário ao preencher os dados nos campos de `usuario` e `senha`.
2. **Login**: Efetuação de login do usuário com os dados que já foram cadastrados anteriormente. O permitindo realizar as operações dentro de ``Tópicos``.

***OBS.: Sistema de exclusão lógica do usuário ainda em desenvolvimento.***

## 🧠 Conhecimentos aplicados
- Estilo de empacotamento em **Package by Layer**;
- Criação de **API REST**;
- **Persistência dos Dados** das informações;
- Uso **JPA** para a autenticação de usuários;
- Utilização de **DTO**s para a trasnferência de dados;
- Autenticação de usuários por via de **Tokens JWT**;
- Criação de **Filters** para barragem de indivíduos não autorizados no sistema;
- Tratamento de **Exceptions** com o *ControllerAdvice*;
- Uso dos Módulos **Spring Data** e **Spring Security**;
- Auxílio do **Spring Boot** para o desenvolvimento da aplicação, utilizando as Beans: **Component**, **Configuration**, **Controller**, **ControllerAdvice**, **Service** e **Repository**.

## 📄 Documentação
Ao inicializar a aplicação, é possível acessar a documentação por via do JSON, no endereço `http://server:port/v3/api-doc`, ou pelo HTML `http://server:port/swagger-ui.html`. Sendo:
- `server`: O nome do servidor ou IP.
- `port`: A porta do servidor.

## 💻 Tecnologias utilizadas no projeto
- [``Auth0 (JWT)``](https://github.com/auth0/java-jwt)
- [``Java 17``](https://docs.oracle.com/en/java/javase/17/docs/api/index.html)
- [``Maven v4.0.0``](https://maven.apache.org/index.html)
- [``MySQL``](https://www.mysql.com/)
- [``Spring Boot``](https://spring.io/projects/spring-boot)
- [``Spring Tool Suite 4 (IDE)``](https://spring.io/tools)
- [``SpringDoc OpenAPI``](https://springdoc.org/)


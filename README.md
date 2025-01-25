# ForumHub - Compartilhando conhecimento 💬📢
Sistema de fórum para a criação de tópicos para sanar dúvidas de cursos e envio de respostas.

## 🔨 Funcionalidades
O sistema possui funcionalidades para as partes de **Tópicos**, **Respostas** e **Usuários**.

### Tópicos
- ***POST /topicos***
  - **Cadastro de um novo tópico**: Ao preencher na requisição os campos: `titulo`, `mensagem`, e `curso`, a requisição é enviada ao sistema e salva no banco de dados.
- ***GET /topicos***
- ***GET /topicos/{idTopico}***
  - **Listagem dos tópicos**: Listagem única (informando o ID) ou de todos os tópicos criados, em forma paginada.
- ***PUT /topicos/{idTopico}***
  - **Atualização de informações**: Ao informar no caminho da url o ID do tópico, é possível alterar as informações do tópico, preenchendo os campos `titulo` e `mensagem` no corpo da requisição, aos novos valores atribuídos.
- ***PUT /topicos/{idTopico}/finalizar***
  - **Finalização de tópico**: Ao informar no caminho da url o ID do tópico e preenchendo o campo de `mensagem`, o tópico é considerado **RESOLVIDO**, o finalizando e inibindo o envio de novas respostas.
- ***DELETE /topicos/{idTopico}***
  - **Exclusão do tópico**: Ao informar no caminho da url o ID do tópico, ele é excluído de forma lógica do sistema (sendo necessário o acesso pelo banco de dados para realizar uma exclusão definitiva do usuário).

*OBS.: "**Exclusão lógica**" é a desativação do usuário do sistema, não sendo possível mais utilizá-lo. E "**Exclusão definitiva**", seria excluir o usuário permanentemente do banco de dados, com o método DELETE, não sendo mais possível recuperá-lo*

### Respostas
*Para todos os métodos de ``Respostas``, será sempre necessário enviar o ID do tópico.*
- ***POST /topicos/{idTopico}/resposta***
  - **Envio de respostas**: Ao preencher o campo `mensagem`, a reposta é registrada e salva no banco de dados.
- ***GET /topicos/{idTopico}/resposta***
- ***GET /topicos/{idTopico}/resposta/{idResposta}***
  - **Listagem de respostas**: Realização de listagem única (informando o ID das respostas) ou de todas as respostas registradas, de forma paginada.
- ***PUT /topicos/{idTopico}/resposta/{idResposta}***
  - **Modificação de resposta**: Ao informar no caminho da url o ID da resposta e preenchendo o campo `mensagem`, a resposta vai ser modificada/atualizada.
- ***DELETE /topicos/{idTopico}/resposta/{idResposta}***
  - **Exclusão da resposta**: Ao informar no caminho da url o ID da resposta, a mesma será excluída de forma lógica do sistema.

### Usuários
- ***POST /usuario/cadastro***
  - **Cadastro**: Realização de cadastro do usuário ao preencher os dados nos campos de `usuario` e `senha`.
- ***POST /usuario/login***
  - **Login**: Efetuação de login do usuário com os dados que já foram cadastrados anteriormente. O permitindo realizar as operações dentro de ``Tópicos``.
- ***GET /usuario***
- ***GET /usuario/{id}***
  - **Visualizar usuários**: Levantamento dos usuários que estão cadastrados no sistema, podendo visualizar todos ou somente um, informando o ID (Ao visualizar os dados dos usuários).
- ***DELETE /usuario/{id}***
  - **Excluir usuário**: Realizada somente pelo próprio usuário, que ao informar o seu ID, pode excluir a sua "conta".

## 🍃 Como inicializar a aplicação
### Pré-Requisitos
- Java 17
- Apache Maven 4.0.0
- MySQL 8.0

### Setup do sistema
1. Primeiramente, clone o repositório:
```bash
git clone https://github.com/LindauroNeto/Echo-ForumHub.git
```

2. Crie o banco de dados para a aplicação (fora isso, as tabelas serão criadas automaticamente)
```bash
CREATE DATABASE <nome_do_banco_de_dados>;
```
3. Atualize as configurações do `application.properties` (tendo em vista as variáveis de ambiente)
![Arquivo application.properties](https://github.com/user-attachments/assets/07ed6ffc-77ed-4661-9630-5bff21070d9e)

### Utilização das variáveis de ambiente
No projeto há 4 variáveis de ambiente
- `MYSQL_URL`: URL que identifica o acesso para o banco de dados.
- `MYSQL_USERNAME`: Nome do usuário do banco de dados.
- `MYSQL_PASSWORD`: Senha para acesso ao banco de dados.
- `Secret`: Senha para a geração de algortimo HMAC256.

### Execução da aplicação
1. Entre no diretório do projeto
```bash
cd <caminho até a pasta>/.../Echo-ForumHub
```
2. Faça o seu build
```bash
mvn clean package
```
3. Escreva o seguinte comando para executá-lo
```bash
java -DMYSQL_URL=<url_da_aplicação> -DMYSQL_USERNAME=<nome_do_usuario_do_banco> -DMYSQL_PASSWORD=<senha_do_usuario_do_banco> -DSecret=<senha_do_algoritmo> -jar target/EchoForumHub-0.0.1-SNAPSHOT.jar
```

### Carregamento do sistema
A imagem seguinte é a que deve aparecer na sua tela!
![Terminal Springboot](https://github.com/user-attachments/assets/627152b4-3ec5-4821-92b6-ba174db964be)

## 📄 Documentação
Ao inicializar a aplicação, é possível acessar a documentação por via do JSON, no endereço `http://server:port/v3/api-doc`.
![Documentação em JSON](https://github.com/user-attachments/assets/0b775673-7cd1-4d7f-bcaa-63f4cbbf4f80)

Ou pelo HTML `http://server:port/swagger-ui.html`.
![Documentação em página web](https://github.com/user-attachments/assets/1a4e7305-8818-49bb-a715-f12911d66962)

Sendo:
- `server`: O nome do servidor ou IP.
- `port`: A porta do servidor.

## 🧠 Conhecimentos aplicados
- Estilo de empacotamento em **Package by Layer**;
- Criação de **API REST**;
- **Persistência dos Dados** das informações;
- Uso **JPA** para a autenticação de usuários;
- Utilização de **DTO**s para a transferência de dados;
- Autenticação de usuários por via de **Tokens JWT**;
- Criação de **Filters** para barragem de indivíduos não autorizados no sistema;
- Tratamento de **Exceptions** com o *ControllerAdvice*;
- Uso dos módulos **Spring Data** e **Spring Security**;
- Auxílio do **Spring Boot** para o desenvolvimento da aplicação, utilizando as Beans: **Component**, **Configuration**, **Controller**, **ControllerAdvice**, **Service** e **Repository**.

## 💻 Tecnologias utilizadas no projeto
- [``Auth0 (JWT)``](https://github.com/auth0/java-jwt)
- [``Java 17``](https://docs.oracle.com/en/java/javase/17/docs/api/index.html)
- [``Maven v4.0.0``](https://maven.apache.org/index.html)
- [``MySQL``](https://www.mysql.com/)
- [``Spring Boot``](https://spring.io/projects/spring-boot)
- [``Spring Tool Suite 4 (IDE)``](https://spring.io/tools)
- [``SpringDoc OpenAPI``](https://springdoc.org/)

## 📜 Licença
Este projeto é licenciado sob a Apache License. Para mais informações, veja o arquivo [LICENSE](LICENSE).

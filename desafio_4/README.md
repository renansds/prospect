# ✨ Desafio Cielo 4

Este é o desafio 4 do Boot Camp Ada Tech + Cielo. Technical Debt: Segurança da Informação

## 🤝 Vunerabilidades detectadas 
O débito técnico identificado é a vulnerabilidade de deserialização de dados não confiáveis no SnakeYAML (CVE-2022-1471),
que é uma biblioteca usada para processar dados YAML. A vulnerabilidade permite que um atacante forneça conteúdo YAML malicioso, que, 
quando desserializado pela biblioteca SnakeYAML, pode levar à execução remota de código no sistema.Isso significa que um invasor 
poderia potencialmente executar código malicioso no servidor, comprometendo a integridade e a segurança dos dados e do sistema.
Solução:
  Para mitigar essa vulnerabilidade, é recomendável remover ou substituir a biblioteca para um versão corrigida SnakeYAML, 
  Optei por atualizar a versão da biblioteca para a versão 2.2 
  ```xml
   	<dependency>
        <groupId>org.yaml</groupId>
        <artifactId>snakeyaml</artifactId>
        <version>2.2</version>
		</dependency>
  ```  
## Requisitos

Certifique-se de que você tenha as seguintes ferramentas instaladas antes de executar o projeto:

- [Java 17](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Docker](https://www.docker.com/)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/)


## 🚀 Como Executar o Projeto, passo a passo!

1. Clone o repositório do GitHub:

   ```shell
   git clone https://github.com/renansds/prospect.git
   ```

2. Navegue até o diretório do projeto:

   ```shell
   cd prospect/desafio_3
   ```
Você pode optar por rodar o projeto de 2 maneiras, dentro de um container docker ou 
em sua própria máquina host.
## Executando diretamente em sua máquina.

1. Compile o projeto usando o Maven:

   ```shell
   ./mvnw clean install
   ```

2. Execute o projeto:

   ```shell
   ./mvnw spring-boot:run
   ```
## Executando com Docker

1. Construa a imagem Docker (certifique-se de ter o Docker instalado):

  ```shell
    docker build -t prospect:3.0.0  -t prospect:latest .
  ```
2. Execute o contêiner:

  ```shell
    docker run  --name prospect-app --rm -d -it -p 8080:8080 prospect
  ```
O projeto será iniciado na porta padrão 8080.
## Status de Saúde da Aplicação

Verifique o status de saúde da aplicação acessando o seguinte endpoint:

- **Endpoint**: [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health)

## Link para documentação da API

Acesse a documentação da API no seguinte link:

- [Swagger UI](http://localhost:8080/swagger-ui/index.html#/)

## Características

A API oferece suporte às seguintes operações:

- **Cadastro de Clientes (Físicos ou Jurídico):**
  - Permite o cadastro de clientes.
  - Utilize o método POST para cadastrar um novo cliente.
  - **Exemplo de Payload para Cliente Físico:**

  ```json
  {
      "nome": "Lillie Runolfsdottir - atualizado",
      "email": "Benjamin33@live.com",
      "cpf": "91719582076",
      "mcc": "4912"
  }
  ```
- **Exemplo do payload de retorno (sucesso)**.
    ```json
    {
        "uuid": "e18dade0-00e3-423e-bce1-07cf261126dd",
        "cpf": "91719582076",
        "mcc": "5912",
        "nome": "Miss Rose MacGyver",
        "email": "Alessandra.Costa40@yahoo.com",
        "links": {
            "self": "http://localhost:8080/cliente/fisico/91719582076"
        }
    }
    ```  
- **Cadastro de Clientes Pessoa Jurídica:**
  - Permite o cadastro de clientes pessoa jurídica.
  - Utilize o método POST para cadastrar um novo cliente pessoa jurídica.
  - **Exemplo de Payload para Cliente Jurídico:**

  ```json
  {
	"razaoSocial": "Razão Social",
	"cnpj": "92.488.614/0001-82",
	"mcc": "5912",
	"contato": {
		"nome": "João Silva Costa",
		"email": "email@valido.com.br",
		"cpf": "917.195.820-76"
	}
  }
  ```
- **Exemplo do payload de retorno** 
    ```json
    {
        "uuid": "7f7a1961-f78e-4bf2-a89c-b32fac8a7694",
        "cnpj": "92488614000182",
        "razaoSocial": "Razão Social",
        "mcc": "5912",
        "contato": {
            "uuid": "46176dab-9d51-407c-a7f5-07324cfd6c3d",
            "email": "email@valido.com.br",
            "nome": "João Silva Costa",
            "cpf": "91719582076",
            "identificacao": "91719582076"
        },
        "links": {
            "self": "http://localhost:8080/cliente/juridico/92488614000182"
        }
    }
    ```
- **Alteração de Clientes:**
  - Possibilita a atualização dos dados de clientes existentes.
  - Utilize o método PUT para realizar alterações.

- **Exclusão de Clientes:**
  - Permite a remoção de clientes do sistema.
  - Utilize o método DELETE para excluir um cliente.

- **Consulta de Clientes:**
  - Oferece a capacidade de consultar informações sobre clientes cadastrados.
  - Utilize o método GET para recuperar os detalhes de um cliente específico.

- **Fila de Atendimento:**
  - Inclusão da fila de atendimento em memória para clientes, que pode ser usada para gerenciar clientes físicos e jurídicos.
  - Esta funcionalidade facilita o gerenciamento de clientes em fila de atendimento.
  - Utilize o método POST

Os exemplos ilustram como é a entrada e saída após um cadastro bem-sucedido de um cliente através da API, para maiores informações e exemplo recomendamos a leitura da nossa [documentação completa (swagger)](#documentação-da-api).
## Relatório de cobertura e testes.

Este projeto inclui testes unitários que cobrem pelo menos 70% do código. Você pode executar os testes da seguinte maneira:

```shell
./mvnw test
```
Além disso, você pode gerar um relatório de cobertura de código usando o JaCoCo. Execute o seguinte comando para gerar o relatório de cobertura:

```shell
./mvnw jacoco:report
```
Após a execução deste comando, você poderá encontrar o relatório de cobertura dentro da pasta:

```plaintext
/prospect
├── target
│   └── site
│       └── jacoco
│           ├── index.html
│           ├── ...
│           └── (outros arquivos de relatório)
```
O relatório principal é o arquivo `index.html`, que pode ser aberto em um navegador da web para visualizar os detalhes da cobertura de código.Para obter mais informações sobre o JaCoCo e como interpretar o relatório de cobertura, consulte a [documentação oficial do JaCoCo](https://www.jacoco.org/jacoco/trunk/doc/index.html).

## Tecnologias e Versões

Aqui estão as principais tecnologias usadas neste projeto e suas versões:
<p align="left"> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a> </p>

### Java Version
- Java 17

### Spring Boot Version
- Spring Boot 3.1.3

## 📝 Licença

Este projeto está licenciado sob a Licença MIT. Consulte o arquivo [LICENSE](LICENSE) para obter detalhes.

---

Siga estas instruções para configurar, executar o projeto. Certifique-se de manter este README atualizado conforme o projeto evolui.
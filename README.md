# ✨ Desafio Cielo 1

Este é o desafio 1 do Boot Camp Ada Tech + Cielo. Ele consiste em uma API REST que permite a criação, alteração, exclusão e consulta de pré-cadastros de clientes. Os dados inicialmente serão armazeandos em memória, utilizando o banco H2.

## Requisitos

Certifique-se de que você tenha as seguintes ferramentas instaladas antes de executar o projeto:

- [Java 17](https://www.oracle.com/java/technologies/javase-downloads.html)
- [IDE IntelliJ](https://www.jetbrains.com/idea/) - (Recomendação para visualização do código fonte).
- [Git](https://git-scm.com/)


## 🚀 Como Executar o Projeto, passo a passo!

1. Clone o repositório do GitHub:

   ```shell
   git clone https://github.com/renansds/prospect.git
   ```

2. Navegue até o diretório do projeto:

   ```shell
   cd prospect
   ```

3. Compile o projeto usando o Maven:

   ```shell
   ./mvnw clean install
   ```

4. Execute o projeto:

   ```shell
   ./mvnw spring-boot:run
   ```

O projeto será iniciado na porta padrão 8080, caso está porta esteja em uso em sua máquina de execução, altere diretamente no arquivo de properties da aplicação.
## Status de Saúde da Aplicação

Você pode verificar o status de saúde da aplicação acessando o seguinte endpoint:

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
            "self": "http://localhost:8080/cliente/fisico/e18dade0-00e3-423e-bce1-07cf261126dd"
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
            "self": "http://localhost:8080/cliente/juridico/7f7a1961-f78e-4bf2-a89c-b32fac8a7694"
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

## 🤝 Como Contribuir

Se você deseja contribuir para este projeto, siga estas etapas:

1. Faça um fork do repositório.

2. Crie uma branch para sua contribuição:

   ```shell
   git checkout -b minha-contribuicao
   ```

3. Faça suas alterações e adicione testes, se necessário.

4. Faça commit das suas alterações:

   ```shell
   git commit -m "Minha contribuição"
   ```

5. Envie suas alterações para o seu fork:

   ```shell
   git push origin minha-contribuicao
   ```

6. Abra um pull request para este repositório.

## 📝 Licença

Este projeto está licenciado sob a Licença MIT. Consulte o arquivo [LICENSE](LICENSE) para obter detalhes.

---

Siga estas instruções para configurar, executar e contribuir para o projeto. Certifique-se de manter este README atualizado conforme o projeto evolui.
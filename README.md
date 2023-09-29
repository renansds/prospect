# ✨ Desafio Cielo

Este repositório contém os desafios do Bootcamp Cielo, cada um organizado em uma pasta correspondente. Abaixo está a estrutura do repositório:

```plaintext
├── desafio_1
├── desafio_2
├── desafio_3
├── desafio_4
├── terraform-desafio-3
├── LICENSE
└── README.md
```

## Desafios

## Desafio 1: Pré-cadastro de Clientes

**Descrição:** Implementação de um pré-cadastro de clientes (prospect) com validações para Pessoa Jurídica e Pessoa Física.

- **Pasta:** `desafio_1`
- **Entregáveis:**
  - Documento Swagger para a API REST.
  - Implementação em Java utilizando Spring Boot.
  - Armazenamento em memória.
  - Cobertura de testes unitários de 70%.

## Desafio 2: Fila de Atendimento

**Descrição:** Criação de uma fila de atendimento para os prospect, com regras de entrada e retirada.

- **Pasta:** `desafio_2`
- **Entregáveis:**
  - Operação na API criada no Desafio 1 para retirar o próximo cliente da fila.
  - Implementação de uma estrutura de fila em Java (FIFO) usando tipos de dados primitivos.
  - Atendimento sequencial dos clientes.
  - Tratamento de fila vazia.
  - Cobertura de testes unitários de 70%.

## Desafio 3: Escalabilidade da Fila de Atendimento

**Descrição:** Resolução do problema de escalabilidade da fila de atendimento identificado no Desafio 2.

- **Pasta:** `desafio_3`
- **Entregáveis:**
  - Implementação de uma nova solução para a fila de atendimento utilizando a solução de mensageria SQS da AWS.

## Desafio 4: Segurança da Informação

**Descrição:** Identificação e correção de débito técnico de Segurança da Informação na aplicação.

- **Pasta:** `desafio_4`
- **Entregáveis:**
  - Identificação do débito técnico, incluindo criticidade e consequências.
  - Planejamento das atividades técnicas para a correção.
  - Implementação da solução.

## Estrutura das Pastas

- `desafio_1`: Contém a implementação do pré-cadastro de clientes com validações.
- `desafio_2`: Contém a implementação da fila de atendimento.
- `desafio_3`: Contém a nova solução para a fila de atendimento com SQS da AWS.
- `desafio_4`: Contém a correção do débito técnico de Segurança da Informação.
- `terraform-desafio-3`: Contém o código para criar a infraestrutura via Terraform para uma fila SQS na AWS.
- `README.md`: Este documento.

Certifique-se de seguir as instruções específicas de cada desafio dentro de suas respectivas pastas para entender melhor os detalhes de cada implementação.

## Requisitos

Certifique-se de que você tenha as seguintes ferramentas instaladas antes de executar o projeto:

- [Java 17](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Docker](https://www.docker.com/)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/)

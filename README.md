# Sistema de Gerenciamento de Reservas

Sistema distribuído baseado em microserviços para gestão de reservas de salas.

## Arquitetura

O sistema é composto por 4 serviços principais:

- **API Gateway** (Porta 8080) - Ponto único de entrada
- **Serviço de Usuários** (8081) - Gerencia cadastro e autenticação
- **Serviço de Salas** (8082) - Gerencia recursos físicos
- **Serviço de Reservas** (8083) - Processa agendamentos

## Tecnologias Utilizadas

- **Linguagem**: Java 17
- **Framework**: Spring Boot 3.x
- **Banco de Dados**: PostgreSQL
- **Mensageria**: Apache Kafka
- **Containerização**: Docker
- **Orquestração**: Docker Compose

## Pré-requisitos

- Docker 20.10+
- Docker Compose 2.5+
- Java 17 (apenas para desenvolvimento)

## Estrutura

```
reserve-manager/
├── api-gateway/
├── user-microservice/
├── room-microservice/
├── reserve-microservice/
└── docker-compose.yml
```

## Instalação e Uso

Para colocar o sistema em funcionamento, siga os passos abaixo:

1.  **Clone o repositório:**

    ```bash
    git clone https://github.com/haaszdev/reserva-final.git
    ```

2.  **Execute o sistema:**

    Navegue até o diretório `reserva-final` e inicie os serviços com Docker Compose:

    ```bash
    cd reserva-final
    docker-compose up --build
    ```

3.  **Acesse a API:**

    A API estará disponível através do gateway no seguinte endereço:

    ```
    http://localhost:8080
    ```
---

## Documentação da API

Os endpoints da API estão acessíveis através do gateway e incluem:

* **`GET /api/users`**: Lista todos os usuários cadastrados no sistema.
* **`GET /api/rooms`**: Lista as salas disponíveis para reserva.
* **`POST /api/reservations`**: Permite a criação de uma nova reserva.

---

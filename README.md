# 🛒 Spring Marketplace API (Modular Monolith)

[![Java](https://img.shields.io/badge/Java-21%2B-orange?style=for-the-badge&logo=openjdk)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)
[![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![MySQL](https://img.shields.io/badge/MySQL-Core_Data-blue?style=for-the-badge&logo=mysql)](https://www.mysql.com/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Ticketing-336791?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![MongoDB](https://img.shields.io/badge/MongoDB-Metadata-47A248?style=for-the-badge&logo=mongodb)](https://www.mongodb.com/)
[![Redis](https://img.shields.io/badge/Redis-Cache_%26_Locks-DC382D?style=for-the-badge&logo=redis)](https://redis.io/)

Uma API RESTful avançada simulando um ecossistema de Marketplace (focado na venda de ingressos e eventos). O projeto foi desenhado sob os princípios de **Domain-Driven Design (DDD)** e arquitetura **Modular Monolith**, separando as responsabilidades de negócio em contextos delimitados (*Bounded Contexts*) que se comunicam através de eventos.

---

## 🏗️ Arquitetura e Engenharia de Software

Este projeto vai muito além de um CRUD tradicional. Ele demonstra a capacidade de desenhar sistemas distribuídos complexos, otimizados para concorrência e alta performance:

* **Arquitetura Monolítica Modular:** O código está dividido em três domínios principais (`registration`, `catalog`, `ticketing`). Isso mantém a base de código coesa, mas prepara o terreno para uma futura extração em microsserviços, se necessário.
* **Comunicação Orientada a Eventos:** Os domínios não se chamam diretamente. Eles reagem a eventos de domínio (ex: `CustomerCreated`, `EventUpdated`) publicados via `ApplicationEventPublisher`, garantindo baixo acoplamento.
* **Persistência Poliglota:** Cada contexto utiliza o banco de dados que melhor atende à sua necessidade:
    * **MySQL:** Dados estruturados de clientes (`Registration`) e eventos core (`Catalog`).
    * **MongoDB:** Metadados flexíveis dos eventos e mapas de assentos (`Catalog`).
    * **PostgreSQL:** Transações financeiras e de ingressos (`Ticketing`).
    * **Redis:** Cache de catálogo e **Distributed Locks** para garantir a concorrência na reserva de assentos (`Ticketing`).
* **Concorrência e Performance:** Utilização de *Virtual Threads* do Java 21 para otimizar operações de I/O bloqueante e `RedisSeatLockRepository` para evitar o problema do *Double Booking* (dupla reserva de assentos).

---

## 🗂️ Estrutura de Bounded Contexts

```text
src/main/java/dio/marketplace/
│
├── registration/      # Domínio de Identidade
│   └── Gerencia o cadastro de usuários (Customers) e publica eventos de criação.
│
├── catalog/           # Domínio de Exibição
│   └── Catálogo de eventos. Utiliza cache no Redis para acelerar a rota de vitrine (/showcase).
│
└── ticketing/         # Domínio Transacional
│   └── Responsável pelo core de vendas. Escuta o catálogo e o registration para validar 
│       compras e utiliza Redis para travar (Lock) assentos durante a seleção.
````
⚙️ Pré-requisitos e Infraestrutura
A infraestrutura completa da aplicação está orquestrada via Docker Compose.

Você precisará de:

Java 21+ (recomendado para uso das Virtual Threads)

Docker e Docker Compose instalados localmente.

O compose.yml subirá automaticamente:

MySQL 9.6 (Portas 3307 e 3308)

MongoDB 8.2 (Porta 27018)

PostgreSQL 18.3 (Porta 5433)

Redis 8.6 (Portas 6380 e 6381)

(Nota: Graças à configuração spring.docker.compose.lifecycle-management=start_only, o Spring Boot gerenciará a inicialização dos contêineres automaticamente ao rodar a aplicação).

🚀 Como Executar o Projeto
1. Clone o repositório:

````Bash
git clone [https://github.com/DanielScarparo/spring-marketplace-api.git](https://github.com/DanielScarparo/spring-marketplace-api.git)
cd spring-marketplace-api
````

2. Inicie a aplicação via Gradle:

No Linux/Mac:

````Bash
./gradlew bootRun
````

No Windows:

````DOS
gradlew.bat bootRun
````
Ao iniciar, o Spring Boot lerá o compose.yml, subirá todos os bancos de dados necessários e inicializará o servidor web.

🛣️ Principais Funcionalidades (Endpoints)
GET /showcase: Retorna a vitrine de eventos disponíveis. Os dados são enriquecidos de forma assíncrona (usando MongoDB) e a resposta final é armazenada em Cache no Redis para performance extrema.

POST /ticketing/events/{eventId}/seats/select: Realiza a reserva de um assento específico. O sistema verifica a existência no banco PostgreSQL e cria um Lock temporário de 30 segundos no Redis (SeatLock) para garantir a exclusividade da compra para o customerId requisitante.

👤 Autor
Daniel Alves Scarparo Silva Desenvolvedor focado na criação de arquiteturas escaláveis, robustas e em boas práticas de Engenharia de Software.

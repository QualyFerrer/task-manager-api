# Task Manager API

REST API para gerenciamento de tarefas desenvolvida com Java e Spring Boot, seguindo boas práticas de arquitetura em camadas, separação de responsabilidades e tratamento estruturado de erros.

---

## Tecnologias

| Tecnologia | Versão | Descrição |
|---|---|---|
| Java | 21 | Linguagem principal |
| Spring Boot | 3.4.5 | Framework principal |
| Spring Data JPA | — | Persistência e repositórios |
| Hibernate | 6.6.x | ORM |
| H2 Database | — | Banco de dados em memória |
| Spring Validation | — | Validação de dados de entrada |
| Springdoc OpenAPI | 2.3.0 | Documentação interativa (Swagger) |
| JUnit 5 | — | Framework de testes |
| Mockito | — | Mocks para testes unitários |
| Maven | — | Gerenciamento de dependências |

---

## Arquitetura

O projeto segue o padrão de arquitetura em camadas amplamente adotado no ecossistema Spring:

```
┌─────────────────────────────────────────┐
│              Cliente HTTP               │
│         (Postman / Swagger UI)          │
└────────────────────┬────────────────────┘
                     │
┌────────────────────▼────────────────────┐
│           TaskController                │
│   @RestController  |  @RequestMapping   │
│   Recebe requisições e retorna respos-  │
│   tas HTTP com os status corretos       │
└────────────────────┬────────────────────┘
                     │
┌────────────────────▼────────────────────┐
│            TaskService                  │
│             @Service                    │
│   Contém toda a lógica de negócio.      │
│   Lança exceções específicas quando     │
│   as regras são violadas                │
└────────────────────┬────────────────────┘
                     │
┌────────────────────▼────────────────────┐
│           TaskRepository                │
│    @Repository  |  JpaRepository        │
│   Comunicação com o banco de dados.     │
│   Queries geradas automaticamente       │
│   pelo Spring Data                      │
└────────────────────┬────────────────────┘
                     │
┌────────────────────▼────────────────────┐
│           H2 Database                   │
│          (em memória)                   │
└─────────────────────────────────────────┘
```

### Fluxo de dados

```
Requisição → DTO de entrada (TaskRequestDto)
                    ↓ validação (@Valid)
              TaskService
                    ↓ mapeamento
              Task (entidade JPA)
                    ↓ persistência
              TaskRepository
                    ↓ retorno
              Task (entidade JPA)
                    ↓ conversão
         DTO de saída (TaskResponseDto)
                    ↓
              Resposta HTTP
```

---

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/cesar/task_manager_api/
│   │   ├── TaskManagerApiApplication.java    # Ponto de entrada
│   │   ├── DataLoader.java                   # Dados iniciais (dev)
│   │   ├── controller/
│   │   │   └── TaskController.java           # Endpoints REST
│   │   ├── service/
│   │   │   └── TaskService.java              # Regras de negócio
│   │   ├── repository/
│   │   │   └── TaskRepository.java           # Acesso ao banco
│   │   ├── entity/
│   │   │   └── Task.java                     # Entidade JPA
│   │   ├── dto/
│   │   │   ├── TaskRequestDto.java           # Entrada da API
│   │   │   └── TaskResponseDto.java          # Saída da API
│   │   ├── enums/
│   │   │   └── Priority.java                 # LOW | MEDIUM | HIGH
│   │   └── exception/
│   │       ├── BusinessException.java        # Regra de negócio violada
│   │       ├── TaskNotFoundException.java    # Tarefa não encontrada
│   │       ├── ErrorResponse.java            # Estrutura do erro
│   │       └── GlobalExceptionHandler.java  # @RestControllerAdvice
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/cesar/task_manager_api/
        ├── TaskManagerApiApplicationTests.java
        ├── controller/
        │   └── TaskControllerTest.java       # @WebMvcTest
        ├── service/
        │   └── TaskServiceTest.java          # @ExtendWith(Mockito)
        └── repository/
            └── TaskRepositoryTest.java       # @DataJpaTest
```

---

## Endpoints

### Base URL
```
http://localhost:8080
```

### Tasks

| Método | Endpoint | Descrição | Status |
|---|---|---|---|
| `POST` | `/tasks` | Cria uma nova tarefa | `201 Created` |
| `GET` | `/tasks` | Lista todas as tarefas (paginado) | `200 OK` |
| `GET` | `/tasks/{id}` | Busca tarefa por ID | `200 OK` |
| `PUT` | `/tasks/{id}` | Atualiza uma tarefa | `200 OK` |
| `PATCH` | `/tasks/{id}/complete` | Marca tarefa como concluída | `200 OK` |
| `DELETE` | `/tasks/{id}` | Remove uma tarefa | `204 No Content` |
| `GET` | `/tasks/priority?level=HIGH` | Filtra por prioridade (`LOW`, `MEDIUM`, `HIGH`) | `200 OK` |
| `GET` | `/tasks/pending` | Lista tarefas não concluídas | `200 OK` |
| `GET` | `/tasks/search?title=texto` | Busca por título (case-insensitive) | `200 OK` |

---

## Exemplos de Uso

### Criar tarefa
```http
POST /tasks
Content-Type: application/json

{
  "title": "Estudar Spring Boot",
  "description": "Praticar endpoints REST",
  "priority": "HIGH"
}
```

**Resposta (`201 Created`):**
```json
{
  "id": 1,
  "title": "Estudar Spring Boot",
  "description": "Praticar endpoints REST",
  "completed": false,
  "priority": "HIGH"
}
```

---

### Buscar tarefa por ID
```http
GET /tasks/1
```

**Resposta (`200 OK`):**
```json
{
  "id": 1,
  "title": "Estudar Spring Boot",
  "description": "Praticar endpoints REST",
  "completed": false,
  "priority": "HIGH"
}
```

**Resposta quando não encontrada (`404 Not Found`):**
```json
{
  "message": "Task not found with id: 1",
  "status": 404,
  "timestamp": "2026-03-30T20:00:00"
}
```

---

### Marcar como concluída
```http
PATCH /tasks/1/complete
```

**Resposta (`200 OK`):**
```json
{
  "id": 1,
  "title": "Estudar Spring Boot",
  "description": "Praticar endpoints REST",
  "completed": true,
  "priority": "HIGH"
}
```

**Resposta quando já concluída (`400 Bad Request`):**
```json
{
  "message": "Task is already completed",
  "status": 400,
  "timestamp": "2026-03-30T20:00:00"
}
```

---

### Validação de campos obrigatórios
```http
POST /tasks
Content-Type: application/json

{
  "title": "",
  "priority": null
}
```

**Resposta (`400 Bad Request`):**
```json
{
  "message": "Validation error",
  "status": 400,
  "errors": [
    "Title is required",
    "Priority is required"
  ],
  "timestamp": "2026-03-30T20:00:00"
}
```

---

### Listar com paginação
```http
GET /tasks?page=0&size=5&sort=priority,desc
```

---

## Regras de Negócio

- Toda tarefa é criada com `completed = false` — o cliente não controla esse campo na criação
- O campo `priority` aceita apenas os valores: `LOW`, `MEDIUM`, `HIGH`
- Não é possível marcar como concluída uma tarefa que já está concluída — retorna `400 Bad Request`
- Os campos `title` (máx. 100 caracteres) e `priority` são obrigatórios
- O campo `description` aceita até 500 caracteres
- Operações sobre IDs inexistentes retornam `404 Not Found` com mensagem descritiva

---

## Como Executar

### Pré-requisitos
- Java 21+
- Maven (ou usar o wrapper incluído no projeto)

### Passos

```bash
# Clone o repositório
git clone https://github.com/QualyFerrer/task-manager-api.git

# Entre na pasta do projeto
cd task-manager-api

# Execute a aplicação (Linux/macOS)
./mvnw spring-boot:run

# Execute a aplicação (Windows)
mvnw.cmd spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

---

## Documentação Interativa

Com a aplicação rodando, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

O Swagger UI lista todos os endpoints com possibilidade de testar as requisições diretamente no navegador.

---

## Console H2

Para visualizar o banco de dados em memória durante o desenvolvimento:

```
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:taskdb
Usuário:  sa
Senha:    (vazio)
```

---

## Testes

O projeto conta com quatro camadas de testes automatizados:

| Camada | Classe | Anotação | O que testa |
|---|---|---|---|
| Unitário | `TaskServiceTest` | `@ExtendWith(MockitoExtension.class)` | Regras de negócio isoladas com mocks (5 testes) |
| Web | `TaskControllerTest` | `@WebMvcTest` | Endpoints, status HTTP e JSON de resposta (4 testes) |
| Repositório | `TaskRepositoryTest` | `@DataJpaTest` | Queries do Spring Data com H2 real (3 testes) |
| Contexto | `TaskManagerApiApplicationTests` | `@SpringBootTest` | Inicialização completa da aplicação (1 teste) |

### Executar todos os testes

```bash
# Linux/macOS
./mvnw test

# Windows
mvnw.cmd test
```

---

## Decisões Técnicas

**Por que DTOs separados para request e response?**
Evitar expor a entidade JPA diretamente na API. O `TaskRequestDto` define exatamente o que o cliente pode enviar, enquanto o `TaskResponseDto` controla o que é retornado — sem vazar campos internos.

**Por que injeção por construtor?**
É a forma recomendada pelo Spring. Torna as dependências explícitas, facilita testes unitários e evita dependências nulas.

**Por que `@Enumerated(EnumType.STRING)`?**
Salvar `"HIGH"` no banco em vez de `2` evita que uma futura reordenação do enum corroa os dados existentes.

**Por que `GlobalExceptionHandler` com `@RestControllerAdvice`?**
Centralizar o tratamento de erros em um único lugar garante respostas padronizadas em toda a API, sem duplicar blocos `try/catch` nos controllers.

---

## Autor

**César Ferrer**  
Estudante de Análise e Desenvolvimento de Sistemas — UNIP  
Desenvolvedor Backend Java | Spring Boot • JPA/Hibernate • SQL • Git

[![GitHub](https://img.shields.io/badge/GitHub-QualyFerrer-181717?style=flat&logo=github)](https://github.com/QualyFerrer)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-César%20Ferrer-0A66C2?style=flat&logo=linkedin)](https://linkedin.com/in/seu-perfil)

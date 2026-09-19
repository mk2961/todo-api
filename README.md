# Todo API

Small Spring Boot Todo REST API used as the backend for the companion REST Assured automation framework.
The current repository uses an in-memory `ConcurrentHashMap`; persistence will be added later.

## Requirements

- Java 21
- Maven (or the included Maven wrapper)

## Run locally

```powershell
.\mvnw.cmd spring-boot:run
```

The API runs on `http://localhost:8081`.

## Endpoints

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/health` | Health check |
| GET | `/todos` | Get all Todos; optional `userId` and `completed` filters |
| GET | `/todos/{id}` | Get a Todo by ID |
| POST | `/todos` | Create a Todo |
| PUT | `/todos/{id}` | Replace/update a Todo |
| PATCH | `/todos/{id}` | Currently uses full replacement semantics |
| DELETE | `/todos/{id}` | Delete a Todo |

## Example request body

```json
{
  "userId": 101,
  "title": "Test my Spring Boot API",
  "completed": false
}
```

## Current design

`TodoController` handles HTTP concerns, `TodoService` owns validation/business logic, and `TodoRepository` owns in-memory storage. Generated IDs use an `AtomicInteger`.

The next planned backend step is persistent database storage.

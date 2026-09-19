# Todo API

Small Spring Boot REST API used as the backend for the companion REST Assured automation framework. The project is intentionally simple enough to study while still demonstrating a realistic layered API and persistent database.

## Architecture

```text
HTTP Client / Automation
        |
        v
TodoController       HTTP routing and response status handling
        |
        v
TodoService          validation and application logic
        |
        v
TodoRepository       Spring Data JPA persistence boundary
        |
        v
H2 Database          file-backed local persistence
```

The separation is intentional: controllers handle HTTP concerns, services own application rules, and repositories own database access. This makes each layer easier to test and change independently.

## Requirements

- Java 21
- Maven, or the included Maven wrapper

## Run locally

```powershell
.\mvnw.cmd spring-boot:run
```

The API runs on `http://localhost:8081`.

## Endpoints

| Method | Endpoint | Purpose |
| --- | --- | --- |
| GET | `/health` | Verify that the application is reachable |
| GET | `/todos` | Get all Todos; optionally filter by `userId` and/or `completed` |
| GET | `/todos/{id}` | Get one Todo by ID |
| POST | `/todos` | Create a Todo |
| PUT | `/todos/{id}` | Replace/update a Todo |
| PATCH | `/todos/{id}` | Currently uses the same full-replacement behavior as PUT |
| DELETE | `/todos/{id}` | Delete a Todo |

## Example request body

```json
{
  "userId": 101,
  "title": "Test my Spring Boot API",
  "completed": false
}
```

The database generates the Todo `id`. A blank or missing title is rejected with HTTP 400.

## Persistence

Todos are stored in a file-backed H2 database under `data/`, so records survive application restarts. The `data/` directory is ignored by Git because it contains machine-local runtime state rather than source code.

The H2 browser console is available at `/h2-console`. The JDBC configuration is documented in `application.properties`. `AUTO_SERVER=TRUE` also allows an external database client such as DBeaver to inspect the database while Spring Boot is running.

## Testing relationship

This API is designed to work with the companion automation project. That framework can test the API contract through REST calls and, in targeted integration tests, verify persistence directly against H2. Keeping those concerns separate demonstrates the difference between API contract testing and API-to-database integration testing.

## PATCH note

`PATCH /todos/{id}` currently delegates to the same update behavior as PUT and therefore expects a complete Todo payload. A production partial-update design would normally use a dedicated request model and update only fields supplied by the client.

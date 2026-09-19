package com.company.todo_api.controller;

import com.company.todo_api.model.Todo;
import com.company.todo_api.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * HTTP boundary for Todo CRUD and filtering operations.
 *
 * The controller translates HTTP requests/responses while TodoService owns
 * validation and application behavior and TodoRepository owns persistence.
 */
@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable("id") int id) {
        return todoService.getTodoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Todo> getTodos(
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "completed", required = false) Boolean completed) {

        return todoService.getTodos(userId, completed);
    }

    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        Todo created = todoService.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable("id") int id,
            @RequestBody Todo todo) {

        return todoService.updateTodo(id, todo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * PATCH intentionally uses the same full-replacement behavior as PUT in
     * this practice API. A production partial update would normally use a
     * dedicated patch DTO and update only fields present in the request.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Todo> patchTodo(
            @PathVariable("id") int id,
            @RequestBody Todo todo) {

        return todoService.updateTodo(id, todo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") int id) {
        return todoService.deleteTodo(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    /**
     * Centralizes domain-validation failures so clients receive a predictable
     * HTTP 400 contract instead of framework-specific exception output.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleInvalidInput(
            IllegalArgumentException exception) {

        return ResponseEntity.badRequest()
                .body(Map.of("error", exception.getMessage()));
    }
}

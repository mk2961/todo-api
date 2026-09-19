package com.company.todo_api.controller;

import com.company.todo_api.model.Todo;
import com.company.todo_api.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Todo CRUD and filtering operations.
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
     * Current PATCH implementation accepts the full Todo payload and uses the
     * same replacement behavior as PUT. True partial-field PATCH semantics can
     * be added later when the API introduces a dedicated patch request model.
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

    /** Converts domain validation failures into a stable HTTP 400 response. */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleInvalidInput(
            IllegalArgumentException exception) {

        return ResponseEntity.badRequest()
                .body(Map.of("error", exception.getMessage()));
    }
}

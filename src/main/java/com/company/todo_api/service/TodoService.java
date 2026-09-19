package com.company.todo_api.service;

import com.company.todo_api.model.Todo;
import com.company.todo_api.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Application/service layer between the REST controller and persistence layer.
 *
 * Business validation belongs here rather than in the controller so the same
 * rules are applied regardless of which HTTP endpoint invokes the operation.
 */
@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(Todo todo) {
        validateTitle(todo);
        return todoRepository.save(todo);
    }

    public Optional<Todo> getTodoById(int id) {
        return todoRepository.findById(id);
    }

    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    public boolean deleteTodo(int id) {
        if (!todoRepository.existsById(id)) {
            return false;
        }

        todoRepository.deleteById(id);
        return true;
    }

    public Optional<Todo> updateTodo(int id, Todo todo) {
        validateTitle(todo);

        if (!todoRepository.existsById(id)) {
            return Optional.empty();
        }

        // The path ID is authoritative so callers cannot update a different
        // record by supplying another ID in the request body.
        todo.setId(id);
        return Optional.of(todoRepository.save(todo));
    }

    /**
     * Selects the repository query that matches the optional API filters.
     * Keeping this decision here leaves the controller focused on HTTP concerns.
     */
    public List<Todo> getTodos(Integer userId, Boolean completed) {
        if (userId != null && completed != null) {
            return todoRepository.findByUserIdAndCompleted(userId, completed);
        }

        if (userId != null) {
            return todoRepository.findByUserId(userId);
        }

        if (completed != null) {
            return todoRepository.findByCompleted(completed);
        }

        return todoRepository.findAll();
    }

    private void validateTitle(Todo todo) {
        if (todo.getTitle() == null || todo.getTitle().isBlank()) {
            throw new IllegalArgumentException("Todo title is required");
        }
    }
}

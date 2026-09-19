package com.company.todo_api.service;

import com.company.todo_api.model.Todo;
import com.company.todo_api.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo createTodo(Todo todo) {
        if (todo.getTitle() == null || todo.getTitle().isBlank()) {
            throw new IllegalArgumentException("Todo title is required");
        }

        return todoRepository.save(todo);
    }

    public Optional<Todo> getTodoById(int id) {
        return todoRepository.findById(id);
    }

    public List<Todo> getTodos() {
        return todoRepository.findAll();
    }

    public boolean deleteTodo(int id) {
        return todoRepository.deleteById(id);
    }

    public Optional<Todo> updateTodo(int id, Todo todo) {
        if (todo.getTitle() == null || todo.getTitle().isBlank()) {
            throw new IllegalArgumentException("Todo title is required");
        }

        return todoRepository.update(id, todo);
    }

    public List<Todo> getTodos(Integer userId, Boolean completed) {
        return todoRepository.findByFilters(userId, completed);
    }
}
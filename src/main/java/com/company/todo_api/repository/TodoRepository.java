package com.company.todo_api.repository;

import com.company.todo_api.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class TodoRepository {

    private final Map<Integer, Todo> todos = new ConcurrentHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    public Todo save(Todo todo) {
        int id = nextId.getAndIncrement();
        todo.setId(id);
        todos.put(id, todo);
        return todo;
    }

    public Optional<Todo> findById(int id) {
        return Optional.ofNullable(todos.get(id));
    }

    public List<Todo> findByFilters(Integer userId, Boolean completed) {
        return todos.values().stream()
                .filter(todo -> userId == null || todo.getUserId() == userId)
                .filter(todo -> completed == null || todo.isCompleted() == completed)
                .toList();
    }

    public boolean deleteById(int id) {
        return todos.remove(id) != null;
    }

    public Optional<Todo> update(int id, Todo todo) {
        todo.setId(id);

        Todo previous = todos.replace(id, todo);

        if (previous == null) {
            return Optional.empty();
        }

        return Optional.of(todo);
    }

    public List<Todo> findAll() {
        return new ArrayList<>(todos.values());
    }

}
package com.company.todo_api.repository;

import com.company.todo_api.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

    List<Todo> findByUserId(Integer userId);

    List<Todo> findByCompleted(Boolean completed);

    List<Todo> findByUserIdAndCompleted(Integer userId, Boolean completed);
}
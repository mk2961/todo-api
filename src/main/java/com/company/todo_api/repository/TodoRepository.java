package com.company.todo_api.repository;

import com.company.todo_api.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Persistence boundary for Todo records.
 *
 * Spring Data JPA generates the query implementations from these method names,
 * keeping SQL and database-access details out of the service layer.
 */
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    List<Todo> findByUserId(Integer userId);

    List<Todo> findByCompleted(Boolean completed);

    List<Todo> findByUserIdAndCompleted(Integer userId, Boolean completed);
}

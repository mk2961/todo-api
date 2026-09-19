package com.company.todo_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application entry point. @SpringBootApplication enables component scanning
 * for the controller, service, repository, and JPA configuration below this package.
 */
@SpringBootApplication
public class TodoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoApiApplication.class, args);
    }
}

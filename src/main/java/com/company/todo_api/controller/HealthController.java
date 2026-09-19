package com.company.todo_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Lightweight endpoint used to verify that the application is reachable
 * without reading or modifying Todo data.
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "Todo API is running";
    }
}

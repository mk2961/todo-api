package com.company.todo_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Basic application-context test. If Spring cannot wire the application,
 * configuration, repository, or database dependencies, this test fails early.
 */
@SpringBootTest
class TodoApiApplicationTests {

    @Test
    void contextLoads() {
        // No assertion is required; successful context startup is the assertion.
    }
}

package com.stocksapi.controller;

import com.stocksapi.BaseTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;

public class BaseControllerTest extends BaseTest {
    @Autowired
    protected MockMvc mockMvc;

    private static final String TEST_DATABASE_IMAGE = "postgres:15-alpine";
    private static final String TEST_DATABASE_NAME = "stock-compass-db";
    private static final String TEST_DATABASE_USER = "db_consumer";
    private static final String TEST_DATABASE_PWD = "cbtXKl7q9rzO";

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(TEST_DATABASE_IMAGE)
            .withDatabaseName(TEST_DATABASE_NAME)
            .withUsername(TEST_DATABASE_USER)
            .withPassword(TEST_DATABASE_PWD)
            .withExposedPorts(5432);

    @BeforeAll
    static void setup() {
        postgres.start();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        String jdbcUrl = String.format("jdbc:postgresql://ep-tight-math-a5csxlts.us-east-2.aws.neon.tech:5432/stock-compass-db");
        registry.add("spring.datasource.url", () -> jdbcUrl);
        registry.add("spring.datasource.username", () -> TEST_DATABASE_USER);
        registry.add("spring.datasource.password", () -> TEST_DATABASE_PWD);
    }

}

package com.example.echo_api.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.redis.testcontainers.RedisContainer;

@ActiveProfiles(value = "test")
@Transactional
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class IntegrationTest {

    @Container
    @ServiceConnection
    protected static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Container
    @ServiceConnection
    protected static final RedisContainer redis = new RedisContainer("redis:latest");

    @Autowired
    private SessionCookieInterceptor restTemplateInterceptor;

    @Autowired
    protected TestRestTemplate restTemplate;

    static {
        redis.start();

        // Dynamically allocate redis host:port
        System.setProperty("spring.data.redis.host", redis.getHost());
        System.setProperty("spring.data.redis.port", redis.getMappedPort(6379).toString());
    }

    /**
     * Initialise the integration test environment by:
     * <ul>
     * <li>Configuring the {@link TestRestTemplate} with the
     * {@link SessionCookieInterceptor}.</li>
     * </ul>
     */
    @BeforeAll
    public void init() {
        // Configure rest template
        restTemplate
            .getRestTemplate()
            .getInterceptors()
            .add(restTemplateInterceptor);
    }

    /**
     * Test ensures that the {@code postgres} container is initialised and running
     * correctly.
     */
    @Test
    public void postgresConnectionEstablished() {
        assertTrue(postgres.isCreated());
        assertTrue(postgres.isRunning());
    }

    /**
     * Test ensures that the {@code redis} container is initialised and running
     * correctly.
     */
    @Test
    public void redisConnectionEstablished() {
        assertTrue(redis.isCreated());
        assertTrue(redis.isRunning());
    }

}

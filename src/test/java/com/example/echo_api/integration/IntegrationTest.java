package com.example.echo_api.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.redis.testcontainers.RedisContainer;

import jakarta.transaction.Transactional;

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

    static {
        redis.start();

        // Dynamically allocate redis host:port
        System.setProperty("spring.data.redis.host", redis.getHost());
        System.setProperty("spring.data.redis.port", redis.getMappedPort(6379).toString());
    }

    @Test
    public void postgresConnectionEstablished() {
        assertTrue(postgres.isCreated());
        assertTrue(postgres.isRunning());
    }

    @Test
    public void redisConnectionEstablished() {
        assertTrue(redis.isCreated());
        assertTrue(redis.isRunning());
    }

}

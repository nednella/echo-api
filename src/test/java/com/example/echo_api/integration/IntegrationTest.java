package com.example.echo_api.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.persistence.dto.request.auth.SignUpRequest;
import com.example.echo_api.persistence.model.User;
import com.redis.testcontainers.RedisContainer;

@ActiveProfiles(value = "test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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

    protected User testUser;

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
     * <li>Configuring a {@code testUser} for integration testing.</li>
     * <li>Obtaining an authenticated session for the {@code testUser}.</li>
     * </ul>
     */
    @BeforeAll
    public void init() {
        // Configure rest template
        restTemplate
            .getRestTemplate()
            .getInterceptors()
            .add(restTemplateInterceptor);

        // Configure test user
        testUser = new User("test", "password1");

        // Authenticate
        obtainAuthenticatedSession();
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

    /**
     * Registers the configured {@code testUser} by sending a POST request to the
     * signup endpoint. The user is inserted into the database and an authenticated
     * session is retrieved from the server.
     */
    private void obtainAuthenticatedSession() {
        // api: POST /api/v1/auth/signup ==> 204 : No Content
        String path = ApiConfig.Auth.SIGNUP;
        SignUpRequest signupForm = new SignUpRequest(testUser.getUsername(), testUser.getPassword());

        HttpEntity<SignUpRequest> request = TestUtils.createJsonRequestEntity(signupForm);
        ResponseEntity<Void> response = restTemplate.postForEntity(path, request, Void.class);

        assertEquals(NO_CONTENT, response.getStatusCode());
        TestUtils.assertSetCookieStartsWith(response, "ECHO_SESSION");
    }

}

package com.example.echo_api.integration.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.persistence.dto.request.auth.SignupRequest;
import com.example.echo_api.persistence.model.user.User;
import com.redis.testcontainers.RedisContainer;

@ActiveProfiles(value = "test")
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {

    @Container
    @ServiceConnection
    protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Container
    @ServiceConnection
    protected static RedisContainer redis = new RedisContainer("redis:latest");

    @Autowired
    protected TestRestTemplate restTemplate;

    @Autowired
    protected SessionCookieInterceptor sessionCookieInterceptor;

    protected User existingUser;

    /**
     * Initialise the integration test environment:
     * <ul>
     * <li>Configure {@link TestRestTemplate} with
     * {@link SessionCookieInterceptor}.</li>
     * <li>Configure a {@code testUser} for integration testing.</li>
     * <li>Obtaining an authenticated session for {@code testUser}.</li>
     * </ul>
     */
    @BeforeAll
    void integrationTestSetup() {
        // Configure rest template
        restTemplate
            .getRestTemplate()
            .getInterceptors()
            .add(sessionCookieInterceptor);

        // Configure test user
        existingUser = new User("test", "password1");

        // Register and authenticate test user
        obtainAuthenticatedSession(existingUser);
    }

    /**
     * Test ensures that the {@code postgres} container is initialised and running
     * correctly.
     */
    @Test
    void postgresConnectionEstablished() {
        assertTrue(postgres.isCreated());
        assertTrue(postgres.isRunning());
    }

    /**
     * Test ensures that the {@code redis} container is initialised and running
     * correctly.
     */
    @Test
    void redisConnectionEstablished() {
        assertTrue(redis.isCreated());
        assertTrue(redis.isRunning());
    }

    /**
     * Registers and authenticates the supplied {@link User} by sending a POST
     * request to the signup endpoint. The user is inserted into the database and an
     * authenticated session is retrieved from the server, and stored in
     * {@link SessionCookieInterceptor}.
     * 
     * @param user the user to be authenticated
     */
    private void obtainAuthenticatedSession(User user) {
        // api: POST /api/v1/auth/signup ==> 204 : No Content
        String path = ApiConfig.Auth.SIGNUP;
        SignupRequest signup = new SignupRequest(user.getUsername(), user.getPassword());

        HttpEntity<SignupRequest> request = TestUtils.createJsonRequestEntity(signup);
        ResponseEntity<Void> response = restTemplate.postForEntity(path, request, Void.class);

        assertEquals(NO_CONTENT, response.getStatusCode());
        TestUtils.assertSetCookieStartsWith(response, "ECHO_SESSION");
    }

}

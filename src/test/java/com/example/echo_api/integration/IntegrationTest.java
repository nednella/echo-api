package com.example.echo_api.integration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.persistence.dto.request.auth.SignUpRequest;
import com.example.echo_api.persistence.model.User;
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
    private SessionCookieInterceptor restTemplateInterceptor;

    protected User testUser;

    /**
     * Workaround method that supplies the Redis container connection details to the
     * application context, so that {@link SessionConfig} can correctly relay the
     * information to the redis {@link LettuceConnectionFactory}.
     * 
     * @param registry the property registry to assign to
     */
    @DynamicPropertySource
    public static void redisInit(DynamicPropertyRegistry registry) {
        redis.start();
        registry.add("spring.data.redis.host", () -> redis.getHost());
        registry.add("spring.data.redis.port", () -> redis.getFirstMappedPort());
    }

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
            .add(restTemplateInterceptor);

        // Configure test user
        testUser = new User("test", "password1");

        // Register and authenticate test user
        obtainAuthenticatedSession(testUser);
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
        SignUpRequest signupForm = new SignUpRequest(user.getUsername(), user.getPassword());

        HttpEntity<SignUpRequest> request = TestUtils.createJsonRequestEntity(signupForm);
        ResponseEntity<Void> response = restTemplate.postForEntity(path, request, Void.class);

        assertEquals(NO_CONTENT, response.getStatusCode());
        TestUtils.assertSetCookieStartsWith(response, "ECHO_SESSION");
    }

}

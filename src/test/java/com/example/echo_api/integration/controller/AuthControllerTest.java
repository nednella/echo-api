package com.example.echo_api.integration.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.controller.auth.AuthController;
import com.example.echo_api.integration.IntegrationTest;
import com.example.echo_api.integration.TestUtils;
import com.example.echo_api.persistence.dto.request.auth.SignInRequest;
import com.example.echo_api.persistence.dto.request.auth.SignUpRequest;
import com.example.echo_api.persistence.dto.response.error.ErrorResponse;
import com.example.echo_api.persistence.model.User;
import com.example.echo_api.service.user.UserService;

/**
 * Integration test class for {@link AuthController}.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest extends IntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    private SignInRequest existingUser;

    /**
     * Set up test environment by initialising {@link SignInRequest} for
     * {@code existingUser}, and registers {@code existingUser} to the database.
     */
    @BeforeAll
    public void setUp() {
        existingUser = new SignInRequest("existing_user", "password-1");
        userService.createUser(existingUser.username(), existingUser.password());
    }

    @Test
    void AuthController_SignIn_Return204() {
        // api: POST /api/v1/auth/login ==> 204 : No Content
        String endpoint = ApiConfig.Auth.LOGIN;

        HttpEntity<SignInRequest> request = TestUtils.createJsonRequestEntity(existingUser);
        ResponseEntity<Void> response = restTemplate.postForEntity(endpoint, request, Void.class);

        // assert the response
        assertEquals(NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        TestUtils.assertSetCookieStartsWith(response, "ECHO_SESSION");
    }

    @Test
    void AuthController_SignUp_Return204() throws Exception {
        // api: POST /api/v1/auth/signup ==> 204 : No Content
        String endpoint = ApiConfig.Auth.SIGNUP;
        SignUpRequest newUser = new SignUpRequest("new_user", "password-1");

        HttpEntity<SignUpRequest> request = TestUtils.createJsonRequestEntity(newUser);
        ResponseEntity<Void> response = restTemplate.postForEntity(endpoint, request, Void.class);

        // assert response
        assertEquals(NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        TestUtils.assertSetCookieStartsWith(response, "ECHO_SESSION");

        // assert db
        User registeredUser = userService.findByUsername(newUser.username());
        assertNotNull(registeredUser);
        assertEquals(newUser.username(), registeredUser.getUsername());
    }

    @Test
    void AuthController_SignUp_Return400UsernameAlreadyExists() {
        // api: POST /api/v1/auth/signup ==> 400 : Username Already Exists
        String endpoint = ApiConfig.Auth.SIGNUP;
        SignUpRequest takenUser = new SignUpRequest(existingUser.username(), existingUser.password());

        HttpEntity<SignUpRequest> request = TestUtils.createJsonRequestEntity(takenUser);
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(endpoint, request, ErrorResponse.class);

        // assert response
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        // assert error
        ErrorResponse error = response.getBody();
        assertNotNull(error);
        assertEquals(BAD_REQUEST.value(), error.status());
        assertEquals("username already exists", error.message());
    }

}

package com.example.echo_api.integration.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.config.ErrorMessageConfig;
import com.example.echo_api.controller.auth.AuthController;
import com.example.echo_api.integration.util.IntegrationTest;
import com.example.echo_api.integration.util.TestUtils;
import com.example.echo_api.persistence.dto.request.auth.LoginRequest;
import com.example.echo_api.persistence.dto.request.auth.SignupRequest;
import com.example.echo_api.persistence.dto.response.error.ErrorResponse;

/**
 * Integration test class for {@link AuthController}.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AuthControllerIT extends IntegrationTest {

    @BeforeAll
    void setUp() {
        sessionCookieInterceptor.disable();
    }

    @AfterAll
    void tearDown() {
        sessionCookieInterceptor.enable();
    }

    @Test
    void AuthController_SignIn_Return204() {
        // api: POST /api/v1/auth/login ==> 204 : No Content
        String endpoint = ApiConfig.Auth.LOGIN;

        // POST a login for the pre-existing testUser
        LoginRequest login = new LoginRequest(existingUser.getUsername(), existingUser.getPassword());

        HttpEntity<LoginRequest> request = TestUtils.createJsonRequestEntity(login);
        ResponseEntity<Void> response = restTemplate.postForEntity(endpoint, request, Void.class);

        // assert the response
        assertEquals(NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        TestUtils.assertSetCookieStartsWith(response, "ECHO_SESSION");
    }

    @Test
    void AuthController_SignUp_Return204() {
        // api: POST /api/v1/auth/signup ==> 204 : No Content
        String endpoint = ApiConfig.Auth.SIGNUP;

        // POST a signup for a new user
        SignupRequest signup = new SignupRequest("new_user", "password1");

        HttpEntity<SignupRequest> request = TestUtils.createJsonRequestEntity(signup);
        ResponseEntity<Void> response = restTemplate.postForEntity(endpoint, request, Void.class);

        // assert response
        assertEquals(NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        TestUtils.assertSetCookieStartsWith(response, "ECHO_SESSION");
    }

    @Test
    void AuthController_SignUp_Return400UsernameAlreadyExists() {
        // api: POST /api/v1/auth/signup ==> 400 : Username Already Exists
        String endpoint = ApiConfig.Auth.SIGNUP;

        // POST a signup for the pre-existing testUser
        SignupRequest signup = new SignupRequest(existingUser.getUsername(), existingUser.getPassword());

        HttpEntity<SignupRequest> request = TestUtils.createJsonRequestEntity(signup);
        ResponseEntity<ErrorResponse> response = restTemplate.postForEntity(endpoint, request, ErrorResponse.class);

        // assert response
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        // assert error
        ErrorResponse error = response.getBody();
        assertNotNull(error);
        assertEquals(BAD_REQUEST.value(), error.status());
        assertEquals(ErrorMessageConfig.USERNAME_ARLEADY_EXISTS, error.message());
    }

}

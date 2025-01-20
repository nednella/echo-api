package com.example.echo_api.integration.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AuthControllerTest extends IntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void AuthController_SignIn_Return204() {
        // api: POST /api/v1/auth/login ==> 204 : No Content
        String endpoint = ApiConfig.Auth.LOGIN;

        // POST a login for the pre-existing testUser
        SignInRequest loginForm = new SignInRequest(testUser.getUsername(), testUser.getPassword());

        HttpEntity<SignInRequest> request = TestUtils.createJsonRequestEntity(loginForm);
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

        // POST a signup for a new user
        SignUpRequest signupForm = new SignUpRequest("new_user", "password1");

        HttpEntity<SignUpRequest> request = TestUtils.createJsonRequestEntity(signupForm);
        ResponseEntity<Void> response = restTemplate.postForEntity(endpoint, request, Void.class);

        // assert response
        assertEquals(NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        TestUtils.assertSetCookieStartsWith(response, "ECHO_SESSION");

        // assert db
        User registeredUser = userService.findByUsername(signupForm.username());
        assertNotNull(registeredUser);
        assertEquals(signupForm.username(), registeredUser.getUsername());
    }

    @Test
    void AuthController_SignUp_Return400UsernameAlreadyExists() {
        // api: POST /api/v1/auth/signup ==> 400 : Username Already Exists
        String endpoint = ApiConfig.Auth.SIGNUP;

        // POST a signup for the pre-existing testUser
        SignUpRequest signupForm = new SignUpRequest(testUser.getUsername(), testUser.getPassword());

        HttpEntity<SignUpRequest> request = TestUtils.createJsonRequestEntity(signupForm);
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

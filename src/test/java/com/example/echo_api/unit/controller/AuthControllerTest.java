package com.example.echo_api.unit.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.controller.auth.AuthController;
import com.example.echo_api.exception.custom.UsernameAlreadyExistsException;
import com.example.echo_api.persistence.dto.request.auth.SignInRequest;
import com.example.echo_api.persistence.dto.request.auth.SignUpRequest;
import com.example.echo_api.service.auth.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test class for {@link AuthController}.
 */
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private SignInRequest validSignInRequest;
    private SignInRequest invalidSignInRequest;

    private SignUpRequest validSignUpRequest;
    private SignUpRequest invalidSignUpRequest;

    /**
     * Sets up valid and invalid {@link SignInRequest} {@link SignUpRequest} objects
     * before each test.
     */
    @BeforeEach
    public void setUp() {
        validSignInRequest = new SignInRequest("admin", "password");
        invalidSignInRequest = new SignInRequest("", "");

        validSignUpRequest = new SignUpRequest("admin", "valid-1-password");
        invalidSignUpRequest = new SignUpRequest("invalid-username", "invalid-password");
    }

    @Test
    public void AuthController_SignIn_Return200() throws Exception {
        // api: POST /api/v1/auth/login ==> 204 : No Content
        String endpoint = ApiConfig.Auth.LOGIN;
        String body = objectMapper.writeValueAsString(validSignInRequest);

        doNothing()
                .when(authService)
                .signIn(anyString(), anyString());

        mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void AuthController_SignIn_Return400InvalidRequest() throws Exception {
        // api: POST /api/v1/auth/login ==> 400 Invalid Request
        String endpoint = ApiConfig.Auth.LOGIN;
        String body = objectMapper.writeValueAsString(invalidSignInRequest);

        doNothing()
                .when(authService)
                .signIn(anyString(), anyString());

        mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("invalid request"))
                .andExpect(jsonPath("$.path").value(endpoint));
    }

    @Test
    public void AuthController_SignIn_Return400UsernameNotFound() throws Exception {
        // api: POST /api/v1/auth/login ==> 400 Username Not Found
        String endpoint = ApiConfig.Auth.LOGIN;
        String body = objectMapper.writeValueAsString(validSignInRequest);

        doThrow(new UsernameNotFoundException(""))
                .when(authService)
                .signIn(anyString(), anyString());

        mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("username or password is incorrect"))
                .andExpect(jsonPath("$.path").value(endpoint));
    }

    @Test
    public void AuthController_SignIn_Return400BadCredentials() throws Exception {
        // api: POST /api/v1/auth/login ==> 400 Bad Credentials
        String endpoint = ApiConfig.Auth.LOGIN;
        String body = objectMapper.writeValueAsString(validSignInRequest);

        doThrow(new BadCredentialsException(""))
                .when(authService)
                .signIn(anyString(), anyString());

        mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("username or password is incorrect"))
                .andExpect(jsonPath("$.path").value(endpoint));
    }

    @Test
    public void AuthController_SignIn_Return401AccountStatusDisabled() throws Exception {
        // api: POST /api/v1/auth/login ==> 401 Account Status - Disabled
        String endpoint = ApiConfig.Auth.LOGIN;
        String body = objectMapper.writeValueAsString(validSignInRequest);

        doThrow(new DisabledException(""))
                .when(authService)
                .signIn(anyString(), anyString());

        mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.message").value("account status is abnormal"))
                .andExpect(jsonPath("$.path").value(endpoint));
    }

    @Test
    public void AuthController_SignIn_Return401AccountStatusLocked() throws Exception {
        // api: POST /api/v1/auth/login ==> 401 Account Status - Locked
        String endpoint = ApiConfig.Auth.LOGIN;
        String body = objectMapper.writeValueAsString(validSignInRequest);

        doThrow(new LockedException(""))
                .when(authService)
                .signIn(anyString(), anyString());

        mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.message").value("account status is abnormal"))
                .andExpect(jsonPath("$.path").value(endpoint));
    }

    @Test
    public void AuthController_SignUp_Return200() throws Exception {
        // api: POST /api/v1/auth/signup ==> 200 : "Registration successful!"
        String endpoint = ApiConfig.Auth.SIGNUP;
        String body = objectMapper.writeValueAsString(validSignUpRequest);

        doNothing()
                .when(authService)
                .signUp(anyString(), anyString());

        mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isNoContent());

    }

    @Test
    public void AuthController_SignUp_Return400InvalidRequest() throws Exception {
        // api: POST /api/v1/auth/signup ==> 400 Invalid Request
        String endpoint = ApiConfig.Auth.SIGNUP;
        String body = objectMapper.writeValueAsString(invalidSignUpRequest);

        doNothing()
                .when(authService)
                .signUp(anyString(), anyString());

        mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("invalid request"))
                .andExpect(jsonPath("$.path").value(endpoint));
    }

    @Test
    public void AuthController_SignUp_Return400UsernameAlreadyExists() throws Exception {
        // api: POST /api/v1/auth/signup ==> 400 Username Already Exists
        String endpoint = ApiConfig.Auth.SIGNUP;
        String body = objectMapper.writeValueAsString(validSignUpRequest);

        doThrow(new UsernameAlreadyExistsException())
                .when(authService)
                .signUp(anyString(), anyString());

        mockMvc.perform(
                post(endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value("username already exists"))
                .andExpect(jsonPath("$.path").value(endpoint));
    }

}

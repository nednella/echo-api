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
import com.example.echo_api.config.ErrorMessageConfig;
import com.example.echo_api.controller.auth.AuthController;
import com.example.echo_api.exception.custom.username.UsernameAlreadyExistsException;
import com.example.echo_api.persistence.dto.request.auth.LoginRequest;
import com.example.echo_api.persistence.dto.request.auth.SignupRequest;
import com.example.echo_api.service.auth.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test class for {@link AuthController}.
 */
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private LoginRequest validLogin;
    private LoginRequest invalidLogin;
    private SignupRequest validSignup;
    private SignupRequest invalidSignup;

    /**
     * Sets up valid and invalid {@link LoginRequest} {@link SignupRequest} objects
     * before each test.
     */
    @BeforeEach
    public void setUp() {
        validLogin = new LoginRequest("admin", "password");
        invalidLogin = new LoginRequest("", "");

        validSignup = new SignupRequest("admin", "valid-1-password");
        invalidSignup = new SignupRequest("invalid-username", "invalid-password");
    }

    @Test
    void AuthController_SignIn_Return204() throws Exception {
        // api: POST /api/v1/auth/login ==> 204 : No Content
        String endpoint = ApiConfig.Auth.LOGIN;
        String body = objectMapper.writeValueAsString(validLogin);

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
    void AuthController_SignIn_Return400InvalidRequest() throws Exception {
        // api: POST /api/v1/auth/login ==> 400 Invalid Request
        String endpoint = ApiConfig.Auth.LOGIN;
        String body = objectMapper.writeValueAsString(invalidLogin);

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
            .andExpect(jsonPath("$.message").value(ErrorMessageConfig.INVALID_REQUEST))
            .andExpect(jsonPath("$.path").value(endpoint));
    }

    @Test
    void AuthController_SignIn_Return400UsernameNotFound() throws Exception {
        // api: POST /api/v1/auth/login ==> 400 Username Not Found
        String endpoint = ApiConfig.Auth.LOGIN;
        String body = objectMapper.writeValueAsString(validLogin);

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
            .andExpect(jsonPath("$.message").value(ErrorMessageConfig.USERNAME_OR_PASSWORD_IS_INCORRECT))
            .andExpect(jsonPath("$.path").value(endpoint));
    }

    @Test
    void AuthController_SignIn_Return400BadCredentials() throws Exception {
        // api: POST /api/v1/auth/login ==> 400 Bad Credentials
        String endpoint = ApiConfig.Auth.LOGIN;
        String body = objectMapper.writeValueAsString(validLogin);

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
            .andExpect(jsonPath("$.message").value(ErrorMessageConfig.USERNAME_OR_PASSWORD_IS_INCORRECT))
            .andExpect(jsonPath("$.path").value(endpoint));
    }

    @Test
    void AuthController_SignIn_Return401AccountStatusDisabled() throws Exception {
        // api: POST /api/v1/auth/login ==> 401 Account Status - Disabled
        String endpoint = ApiConfig.Auth.LOGIN;
        String body = objectMapper.writeValueAsString(validLogin);

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
            .andExpect(jsonPath("$.message").value(ErrorMessageConfig.ACCOUNT_STATUS))
            .andExpect(jsonPath("$.path").value(endpoint));
    }

    @Test
    void AuthController_SignIn_Return401AccountStatusLocked() throws Exception {
        // api: POST /api/v1/auth/login ==> 401 Account Status - Locked
        String endpoint = ApiConfig.Auth.LOGIN;
        String body = objectMapper.writeValueAsString(validLogin);

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
            .andExpect(jsonPath("$.message").value(ErrorMessageConfig.ACCOUNT_STATUS))
            .andExpect(jsonPath("$.path").value(endpoint));
    }

    @Test
    void AuthController_SignUp_Return204() throws Exception {
        // api: POST /api/v1/auth/signup ==> 204 : No Content
        String endpoint = ApiConfig.Auth.SIGNUP;
        String body = objectMapper.writeValueAsString(validSignup);

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
    void AuthController_SignUp_Return400InvalidRequest() throws Exception {
        // api: POST /api/v1/auth/signup ==> 400 Invalid Request
        String endpoint = ApiConfig.Auth.SIGNUP;
        String body = objectMapper.writeValueAsString(invalidSignup);

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
            .andExpect(jsonPath("$.message").value(ErrorMessageConfig.INVALID_REQUEST))
            .andExpect(jsonPath("$.path").value(endpoint));
    }

    @Test
    void AuthController_SignUp_Return400UsernameAlreadyExists() throws Exception {
        // api: POST /api/v1/auth/signup ==> 400 Username Already Exists
        String endpoint = ApiConfig.Auth.SIGNUP;
        String body = objectMapper.writeValueAsString(validSignup);

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
            .andExpect(jsonPath("$.message").value(ErrorMessageConfig.USERNAME_ARLEADY_EXISTS))
            .andExpect(jsonPath("$.path").value(endpoint));
    }

}

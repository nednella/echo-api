package com.example.echo_api.unit.controller;

import static org.junit.Assert.assertEquals;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.controller.account.AccountController;
import com.example.echo_api.exception.custom.password.ConfirmationPasswordMismatchException;
import com.example.echo_api.exception.custom.password.IncorrectCurrentPasswordException;
import com.example.echo_api.exception.custom.password.NewPasswordEqualsOldPasswordException;
import com.example.echo_api.exception.custom.username.UsernameAlreadyExistsException;
import com.example.echo_api.persistence.dto.request.account.UpdatePasswordRequest;
import com.example.echo_api.persistence.dto.response.error.ErrorResponse;
import com.example.echo_api.persistence.model.User;
import com.example.echo_api.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test class for {@link AccountController}.
 */
@WebMvcTest(AccountController.class)
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    /**
     * Sets up a {@link User} object before each test.
     */
    @BeforeEach
    public void setUp() {
        testUser = new User(
            "testUsername",
            "testPassword");
    }

    @Test
    void AccountController_UsernameAvailable_Return200True() throws Exception {
        // api: GET /api/v1/account/username-available?username={...} ==> 200 : True
        String path = ApiConfig.Account.USERNAME_AVAILABLE;

        when(userService.existsByUsername(testUser.getUsername()))
            .thenReturn(false);

        mockMvc.perform(get(path)
            .param("username", testUser.getUsername()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("true"));
    }

    @Test
    void AccountController_UsernameAvailable_Return200False() throws Exception {
        // api: GET /api/v1/account/username-available?username={...} ==> 200 : False
        String path = ApiConfig.Account.USERNAME_AVAILABLE;

        when(userService.existsByUsername(testUser.getUsername()))
            .thenReturn(true);

        mockMvc
            .perform(get(path)
                .param("username", testUser.getUsername()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string("false"));
    }

    @Test
    void AccountController_UsernameAvailable_Throw400InvalidRequest() throws Exception {
        // api: GET /api/v1/account/username-available?username={...} ==> 400 : Invalid
        // Request
        String path = ApiConfig.Account.USERNAME_AVAILABLE;

        String response = mockMvc
            .perform(get(path)
                .param("username", "invalid-username"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorResponse error = objectMapper.readValue(response, ErrorResponse.class);
        assertEquals(400, error.status());
        assertEquals("invalid request", error.message());
        assertEquals(path, error.path());
    }

    @Test
    void AccountController_UpdateUsername_Return204() throws Exception {
        // api: PUT /api/v1/account/username ==> 204 : No Content
        String path = ApiConfig.Account.UPDATE_USERNAME;

        doNothing().when(userService).updateUsername(testUser.getUsername());

        mockMvc
            .perform(put(path)
                .param("username", testUser.getUsername()))
            .andDo(print())
            .andExpect(status().isNoContent());
    }

    @Test
    void AccountController_UpdateUsername_Return400UsernameAlreadyExists() throws Exception {
        // api: PUT /api/v1/account/username ==> 400 : UsernameAlreadyExists
        String path = ApiConfig.Account.UPDATE_USERNAME;

        doThrow(new UsernameAlreadyExistsException()).when(userService).updateUsername(testUser.getUsername());

        String response = mockMvc
            .perform(put(path)
                .param("username", testUser.getUsername()))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorResponse error = objectMapper.readValue(response, ErrorResponse.class);
        assertEquals(400, error.status());
        assertEquals("Username already exists.", error.message());
        assertEquals(path, error.path());
    }

    @Test
    void AccountController_UpdatePassword_Return204() throws Exception {
        // api: PUT /api/v1/account/password ==> 204 : No Content
        String path = ApiConfig.Account.UPDATE_PASSWORD;

        UpdatePasswordRequest request = new UpdatePasswordRequest(
            "current_password",
            "new-password-1",
            "new-password-1");

        String body = objectMapper.writeValueAsString(request);

        doNothing().when(userService).updatePassword(request);

        mockMvc
            .perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andExpect(status().isNoContent());
    }

    @Test
    void AccountController_UpdatePassword_Return400ConfirmationPasswordMismatch() throws Exception {
        // api: PUT /api/v1/account/password ==> 400 : ConfirmationPasswordMismatch
        String path = ApiConfig.Account.UPDATE_PASSWORD;

        UpdatePasswordRequest request = new UpdatePasswordRequest(
            "current_password",
            "new-password-1",
            "this-password-doesnt-match");

        String body = objectMapper.writeValueAsString(request);

        doThrow(new ConfirmationPasswordMismatchException()).when(userService).updatePassword(request);

        String response = mockMvc
            .perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorResponse error = objectMapper.readValue(response, ErrorResponse.class);
        assertEquals(400, error.status());
        assertEquals("Confirmation password does not match the new password.", error.message());
        assertEquals(path, error.path());
    }

    @Test
    void AccountController_UpdatePassword_Return400NewPasswordEqualsOldPassword() throws Exception {
        // api: PUT /api/v1/account/password ==> 400 : NewPasswordEqualsOldPassword
        String path = ApiConfig.Account.UPDATE_PASSWORD;

        UpdatePasswordRequest request = new UpdatePasswordRequest(
            "current_password-1",
            "current-password-1",
            "current-password-1");

        String body = objectMapper.writeValueAsString(request);

        doThrow(new NewPasswordEqualsOldPasswordException()).when(userService).updatePassword(request);

        String response = mockMvc
            .perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorResponse error = objectMapper.readValue(response, ErrorResponse.class);
        assertEquals(400, error.status());
        assertEquals("New password cannot be the same as the current password.", error.message());
        assertEquals(path, error.path());
    }

    @Test
    void AccountController_UpdatePassword_Return400IncorrectCurrentPassword() throws Exception {
        // api: PUT /api/v1/account/password ==> 400 : IncorrectCurrentPassword
        String path = ApiConfig.Account.UPDATE_PASSWORD;

        UpdatePasswordRequest request = new UpdatePasswordRequest(
            "wrong-password",
            "new-password-1",
            "new-password-1");

        String body = objectMapper.writeValueAsString(request);

        doThrow(new IncorrectCurrentPasswordException()).when(userService).updatePassword(request);

        String response = mockMvc
            .perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorResponse error = objectMapper.readValue(response, ErrorResponse.class);
        assertEquals(400, error.status());
        assertEquals("Incorrect current password.", error.message());
        assertEquals(path, error.path());
    }

}

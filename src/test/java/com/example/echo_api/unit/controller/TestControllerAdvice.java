package com.example.echo_api.unit.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.config.ErrorMessageConfig;
import com.example.echo_api.controller.test.TestController;
import com.example.echo_api.exception.custom.username.UsernameNotFoundException;
import com.example.echo_api.persistence.model.User;
import com.example.echo_api.service.account.AccountService;

/**
 * Unit test class for {@link TestController}.
 */
@WebMvcTest(TestController.class)
@AutoConfigureMockMvc(addFilters = false)
class TestControllerAdvice {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

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
    void UserController_FindAll_ReturnListOfUser() throws Exception {
        // api: GET /api/v1/user/list ==> 200 : List<User>
        String endpoint = ApiConfig.Test.FIND_ALL;

        when(accountService.findAll())
            .thenReturn(List.of(testUser));

        mockMvc.perform(get(endpoint))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].username").value(testUser.getUsername()));
    }

    @Test
    void UserController_FindAll_ReturnListOfEmpty() throws Exception {
        // api: GET /api/v1/user/list ==> 200 : No Content
        String endpoint = ApiConfig.Test.FIND_ALL;

        when(accountService.findAll())
            .thenReturn(List.of());

        mockMvc.perform(get(endpoint))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().string("[]"));
    }

    @Test
    void UserController_FindByUsername_ReturnUser() throws Exception {
        // api: GET /api/v1/user/list/{username} ==> 200 : User
        String endpoint = ApiConfig.Test.FIND_ALL + "/" + testUser.getUsername();

        when(accountService.findByUsername(testUser.getUsername()))
            .thenReturn(testUser);

        mockMvc.perform(get(endpoint))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.username").value(testUser.getUsername()));

    }

    @Test
    void UserController_FindByUsername_Return400UsernameNotFound() throws Exception {
        // api: GET /api/v1/user/list/{username} ==> 400 : Username Not Found
        String endpoint = ApiConfig.Test.FIND_ALL + "/" + testUser.getUsername();

        when(accountService.findByUsername(testUser.getUsername()))
            .thenThrow(new UsernameNotFoundException());

        mockMvc.perform(get(endpoint))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value(ErrorMessageConfig.USERNAME_NOT_FOUND))
            .andExpect(jsonPath("$.path").value(endpoint));
    }

}

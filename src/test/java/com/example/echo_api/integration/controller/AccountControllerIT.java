package com.example.echo_api.integration.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpMethod.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.controller.auth.AuthController;
import com.example.echo_api.integration.IntegrationTest;
import com.example.echo_api.integration.TestUtils;
import com.example.echo_api.persistence.dto.request.account.UpdatePasswordRequest;
import com.example.echo_api.persistence.dto.response.error.ErrorResponse;

/**
 * Integration test class for {@link AuthController}.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AccountControllerIT extends IntegrationTest {

    @Test
    void AccountController_IsUsernameAvailable_Return200True() {
        // api: GET /api/v1/account/username?username={...} ==> 200 : True
        String path = ApiConfig.Account.UPDATE_USERNAME + "?username=" + "unique_name_123";

        ResponseEntity<String> response = restTemplate.getForEntity(path, String.class);

        // assert the response
        assertEquals(OK, response.getStatusCode());
        assertEquals("true", response.getBody());
    }

    @Test
    void AccountController_IsUsernameAvailable_Return200False() {
        // api: GET /api/v1/account/username?username={...} ==> 200 : False
        String path = ApiConfig.Account.UPDATE_USERNAME + "?username=" + existingUser.getUsername();

        ResponseEntity<String> response = restTemplate.getForEntity(path, String.class);

        // assert the response
        assertEquals(OK, response.getStatusCode());
        assertEquals("false", response.getBody());
    }

    @Test
    @Transactional // rollback
    void AccountController_UpdateUsername_Return204() {
        // api: PUT /api/v1/account/username ==> 204 : No Content
        String path = ApiConfig.Account.UPDATE_USERNAME + "?username=" + "new_username";

        // PUT the username update
        ResponseEntity<Void> response1 = restTemplate.exchange(path, PUT, null, Void.class);
        assertEquals(NO_CONTENT, response1.getStatusCode());

        // GET the updated username
        ResponseEntity<String> response2 = restTemplate.getForEntity(path, String.class);
        assertEquals(OK, response2.getStatusCode());
        assertEquals("false", response2.getBody());
    }

    @Test
    void AccountController_UpdateUsername_Return400UsernameAlreadyExists() {
        // api: PUT /api/v1/account/username ==> 400 : UsernameAlreadyExists
        String path = ApiConfig.Account.UPDATE_USERNAME + "?username=" + existingUser.getUsername();

        ResponseEntity<ErrorResponse> response = restTemplate.exchange(path, PUT, null, ErrorResponse.class);

        // assert response
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        // assert error
        ErrorResponse error = response.getBody();
        assertNotNull(error);
        assertEquals(BAD_REQUEST.value(), error.status());
        assertEquals("Username already exists.", error.message());
    }

    @Test
    @Transactional // rollback
    void AccountController_UpdatePassword_Return204() {
        // api: PUT /api/v1/account/password ==> 204 : No Content
        String path = ApiConfig.Account.UPDATE_PASSWORD;
        UpdatePasswordRequest update = new UpdatePasswordRequest(existingUser.getPassword(), "new-pw1", "new-pw1");

        HttpEntity<UpdatePasswordRequest> request = TestUtils.createJsonRequestEntity(update);
        ResponseEntity<Void> response = restTemplate.exchange(path, PUT, request, Void.class);

        assertEquals(NO_CONTENT, response.getStatusCode());
    }

    @Test
    void AccountController_UpdatePassword_Return400IncorrectCurrentPassword() {
        // api: PUT /api/v1/account/password ==> 400 : IncorrectCurrentPassword
        String path = ApiConfig.Account.UPDATE_PASSWORD;
        UpdatePasswordRequest update = new UpdatePasswordRequest("wrong-password", "new-pw1", "new-pw1");

        HttpEntity<UpdatePasswordRequest> request = TestUtils.createJsonRequestEntity(update);
        ResponseEntity<ErrorResponse> response = restTemplate.exchange(path, PUT, request, ErrorResponse.class);

        // assert response
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        // assert error
        ErrorResponse error = response.getBody();
        assertNotNull(error);
        assertEquals(BAD_REQUEST.value(), error.status());
        assertEquals("Incorrect current password.", error.message());
    }

}

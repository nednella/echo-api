package com.example.echo_api.integration.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpMethod.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.config.ErrorMessageConfig;
import com.example.echo_api.controller.auth.AuthController;
import com.example.echo_api.integration.util.IntegrationTest;
import com.example.echo_api.integration.util.TestUtils;
import com.example.echo_api.persistence.dto.request.profile.UpdateProfileDTO;
import com.example.echo_api.persistence.dto.response.error.ErrorDTO;
import com.example.echo_api.persistence.dto.response.profile.ProfileDTO;

/**
 * Integration test class for {@link AuthController}.
 */
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ProfileControllerIT extends IntegrationTest {

    @Test
    void ProfileController_GetMe_ReturnProfileResponse() {
        // api: GET /api/v1/profile/me ==> 200 : ProfileResponse
        String path = ApiConfig.Profile.GET_ME;

        ResponseEntity<ProfileDTO> response = restTemplate.getForEntity(path, ProfileDTO.class);

        // assert response
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // assert body
        ProfileDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(existingAccount.getUsername(), body.username());
    }

    @Test
    void ProfileController_UpdateMe_Return204NoContent() {
        // api: PUT /api/v1/profile/me ==> 204 : No Content
        String path = ApiConfig.Profile.UPDATE_ME;

        UpdateProfileDTO body = new UpdateProfileDTO(
            "name",
            "bio",
            "location");

        HttpEntity<UpdateProfileDTO> request = TestUtils.createJsonRequestEntity(body);
        ResponseEntity<Void> response = restTemplate.exchange(path, PUT, request, Void.class);

        // assert response
        assertEquals(NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void ProfileController_GetByUsername_ReturnProfileResponse() {
        // api: GET /api/v1/profile/{username} ==> 200 : ProfileResponse
        String path = ApiConfig.Profile.GET_BY_USERNAME;

        ResponseEntity<ProfileDTO> response = restTemplate.getForEntity(
            path, ProfileDTO.class, existingAccount.getUsername());

        // assert response
        assertEquals(OK, response.getStatusCode());
        assertNotNull(response.getBody());

        // assert body
        ProfileDTO body = response.getBody();
        assertNotNull(body);
        assertEquals(existingAccount.getUsername(), body.username());
    }

    @Test
    void ProfileController_GetByUsername_Throw400UsernameNotFound() {
        // api: GET /api/v1/profile/{username} ==> 400 : UsernameNotFound
        String path = ApiConfig.Profile.GET_BY_USERNAME;

        ResponseEntity<ErrorDTO> response = restTemplate.getForEntity(
            path, ErrorDTO.class, "non-existent-user");

        // assert response
        assertEquals(BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());

        // assert error
        ErrorDTO error = response.getBody();
        assertNotNull(error);
        assertEquals(BAD_REQUEST.value(), error.status());
        assertEquals(ErrorMessageConfig.USERNAME_NOT_FOUND, error.message());
    }

}

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
    void ProfileController_GetMe_ReturnProfileDTO() {
        // api: GET /api/v1/profile/me ==> 200 : ProfileDTO
        String path = ApiConfig.Profile.ME;

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
        String path = ApiConfig.Profile.ME;

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
    void ProfileController_GetByUsername_ReturnProfileDTO() {
        // api: GET /api/v1/profile/{username} ==> 200 : ProfileDTO
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

    // ---- follow ----

    @Test
    void ProfileController_Follow_Retun204NoContent() {
    }

    @Test
    void ProfileController_Follow_Throw400UsernameNotFound() {
    }

    @Test
    void ProfileController_Follow_ThrowSelfActionException() {
    }

    @Test
    void ProfileController_Follow_ThrowAlreadyFollowingException() {
    }

    @Test
    void ProfileController_Follow_ThrowBlockedException() {
    }

    // ---- unfollow ----

    @Test
    void ProfileController_Unfollow_Retun204NoContent() {
    }

    @Test
    void ProfileController_Unfollow_Throw400UsernameNotFound() {
    }

    @Test
    void ProfileController_Unfollow_ThrowSelfActionException() {
    }

    @Test
    void ProfileController_Unfollow_ThrowNotFollowingException() {
    }

    // ---- block ----

    @Test
    void ProfileController_Block_Retun204NoContent() {
    }

    @Test
    void ProfileController_Block_Throw400UsernameNotFound() {
    }

    @Test
    void ProfileController_Block_ThrowSelfActionException() {
    }

    @Test
    void ProfileController_Block_ThrowAlreadyBlockingException() {
    }

    // ---- unblock ----

    @Test
    void ProfileController_Unblock_Retun204NoContent() {
    }

    @Test
    void ProfileController_Unblock_Throw400UsernameNotFound() {
    }

    @Test
    void ProfileController_Unblock_ThrowSelfActionException() {
    }

    @Test
    void ProfileController_Unblock_ThrowNotBlockingException() {
    }

}

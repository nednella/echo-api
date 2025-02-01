package com.example.echo_api.unit.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.config.ErrorMessageConfig;
import com.example.echo_api.controller.profile.ProfileController;
import com.example.echo_api.exception.custom.username.UsernameNotFoundException;
import com.example.echo_api.persistence.dto.request.profile.UpdateProfileDTO;
import com.example.echo_api.persistence.dto.response.error.ErrorDTO;
import com.example.echo_api.persistence.dto.response.profile.MetricsDTO;
import com.example.echo_api.persistence.dto.response.profile.ProfileDTO;
import com.example.echo_api.persistence.dto.response.profile.RelationshipDTO;
import com.example.echo_api.persistence.mapper.MetricsMapper;
import com.example.echo_api.persistence.mapper.ProfileMapper;
import com.example.echo_api.persistence.model.account.Account;
import com.example.echo_api.persistence.model.profile.Metrics;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.service.profile.ProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test class for {@link ProfileController}.
 */
@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void ProfileController_GetMe_ReturnProfileDTO() throws Exception {
        // api: GET /api/v1/profile/me ==> 200 : ProfileResponse
        String path = ApiConfig.Profile.ME;

        Account account = new Account("test", "test");
        Profile profile = new Profile(account);
        Metrics metrics = new Metrics(profile);
        MetricsDTO metricsDto = MetricsMapper.toDTO(metrics);
        ProfileDTO expected = ProfileMapper.toDTO(profile, metricsDto, null);

        when(profileService.getMe()).thenReturn(expected);

        String response = mockMvc
            .perform(get(path))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ProfileDTO actual = objectMapper.readValue(response, ProfileDTO.class);
        assertEquals(expected, actual);
        verify(profileService, times(1)).getMe();
    }

    @Test
    void ProfileController_GetMe_Throw400UsernameNotFound() throws Exception {
        // api: GET /api/v1/profile/me ==> 400 : UsernameNotFound
        String path = ApiConfig.Profile.ME;

        when(profileService.getMe()).thenThrow(new UsernameNotFoundException());

        String response = mockMvc
            .perform(get(path))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorDTO expected = new ErrorDTO(
            HttpStatus.BAD_REQUEST,
            ErrorMessageConfig.USERNAME_NOT_FOUND,
            null,
            path);

        ErrorDTO actual = objectMapper.readValue(response, ErrorDTO.class);

        assertEquals(expected, actual);
        verify(profileService, times(1)).getMe();
    }

    @Test
    void ProfileController_UpdateMe_Return204NoContent() throws Exception {
        // api: PUT /api/v1/profile/me ==> 204 : No Content
        String path = ApiConfig.Profile.ME;

        UpdateProfileDTO request = new UpdateProfileDTO(
            "name",
            "bio",
            "location");

        String body = objectMapper.writeValueAsString(request);

        doNothing().when(profileService).updateMeProfile(request);

        mockMvc
            .perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andExpect(status().isNoContent());

        verify(profileService, times(1)).updateMeProfile(request);
    }

    @Test
    void ProfileController_UpdateMe_Throw400InvalidRequest_NameExceeds50Characters() throws Exception {
        // api: PUT /api/v1/profile/me ==> 400 : Invalid Request
        String path = ApiConfig.Profile.ME;

        UpdateProfileDTO request = new UpdateProfileDTO(
            "ThisNameIsTooLongBy......................1Character",
            "bio",
            "location");

        String body = objectMapper.writeValueAsString(request);

        doNothing().when(profileService).updateMeProfile(request);

        String response = mockMvc
            .perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorDTO expected = new ErrorDTO(
            HttpStatus.BAD_REQUEST,
            ErrorMessageConfig.INVALID_REQUEST,
            "Name must not exceed 50 characters.",
            path);

        ErrorDTO actual = objectMapper.readValue(response, ErrorDTO.class);

        assertEquals(expected, actual);
        verify(profileService, never()).updateMeProfile(request);
    }

    @Test
    void ProfileController_UpdateMe_Throw400InvalidRequest_BioExceeds160Characters() throws Exception {
        // api: PUT /api/v1/profile/me ==> 400 : Invalid Request
        String path = ApiConfig.Profile.ME;

        UpdateProfileDTO request = new UpdateProfileDTO(
            "name",
            "ThisBioIsTooLongBy.....................................................................................................................................1Character",
            "location");

        String body = objectMapper.writeValueAsString(request);

        doNothing().when(profileService).updateMeProfile(request);

        String response = mockMvc
            .perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorDTO expected = new ErrorDTO(
            HttpStatus.BAD_REQUEST,
            ErrorMessageConfig.INVALID_REQUEST,
            "Bio must not exceed 160 characters.",
            path);

        ErrorDTO actual = objectMapper.readValue(response, ErrorDTO.class);

        assertEquals(expected, actual);
        verify(profileService, never()).updateMeProfile(request);
    }

    @Test
    void ProfileController_UpdateMe_Throw400InvalidRequest_LocationExceeds30Characters() throws Exception {
        // api: PUT /api/v1/profile/me ==> 400 : Invalid Request
        String path = ApiConfig.Profile.ME;

        UpdateProfileDTO request = new UpdateProfileDTO(
            "name",
            "bio",
            "ThisLocationIsTooLongBy4Characters");

        String body = objectMapper.writeValueAsString(request);

        doNothing().when(profileService).updateMeProfile(request);

        String response = mockMvc
            .perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorDTO expected = new ErrorDTO(
            HttpStatus.BAD_REQUEST,
            ErrorMessageConfig.INVALID_REQUEST,
            "Location must not exceed 30 characters.",
            path);

        ErrorDTO actual = objectMapper.readValue(response, ErrorDTO.class);

        assertEquals(expected, actual);
        verify(profileService, never()).updateMeProfile(request);
    }

    @Test
    void ProfileController_GetByUsername_ReturnProfileDTO() throws Exception {
        // api: GET /api/v1/profile/{username} ==> 200 : ProfileDTO
        String path = ApiConfig.Profile.GET_BY_USERNAME;

        Account account = new Account("test", "test");
        Profile profile = new Profile(account);
        Metrics metrics = new Metrics(profile);
        MetricsDTO metricsDto = MetricsMapper.toDTO(metrics);
        RelationshipDTO relationshipDto = new RelationshipDTO(false, false, false, false);
        ProfileDTO expected = ProfileMapper.toDTO(profile, metricsDto, relationshipDto);

        when(profileService.getByUsername(expected.username())).thenReturn(expected);

        String response = mockMvc
            .perform(get(path, expected.username()))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ProfileDTO actual = objectMapper.readValue(response, ProfileDTO.class);
        assertEquals(expected, actual);
        verify(profileService, times(1)).getByUsername(expected.username());
    }

    @Test
    void ProfileController_GetByUsername_Throw400UsernameNotFound() throws Exception {
        // api: GET /api/v1/profile/{username} ==> 400 : UsernameNotFound
        String path = ApiConfig.Profile.GET_BY_USERNAME;

        when(profileService.getByUsername("non-existent-user")).thenThrow(new UsernameNotFoundException());

        String response = mockMvc
            .perform(get(path, "non-existent-user"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorDTO expected = new ErrorDTO(
            HttpStatus.BAD_REQUEST,
            ErrorMessageConfig.USERNAME_NOT_FOUND,
            null,
            path);

        ErrorDTO actual = objectMapper.readValue(response, ErrorDTO.class);

        assertEquals(expected, actual);
        verify(profileService, times(1)).getByUsername("non-existent-user");
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

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
import com.example.echo_api.persistence.dto.request.profile.UpdateProfileInfoRequest;
import com.example.echo_api.persistence.dto.response.error.ErrorResponse;
import com.example.echo_api.persistence.dto.response.profile.ProfileResponse;
import com.example.echo_api.persistence.mapper.ProfileMapper;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.model.user.User;
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
    void ProfileController_GetMe_ReturnProfileResponse() throws Exception {
        // api: GET /api/v1/profile/me ==> 200 : ProfileResponse
        String path = ApiConfig.Profile.GET_ME;

        User user = new User("test", "test");
        Profile profile = new Profile(user);
        ProfileResponse expected = ProfileMapper.toResponse(profile);

        when(profileService.getMe()).thenReturn(expected);

        String response = mockMvc
            .perform(get(path))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ProfileResponse actual = objectMapper.readValue(response, ProfileResponse.class);
        assertEquals(expected, actual);
        verify(profileService, times(1)).getMe();
    }

    @Test
    void ProfileController_GetMe_Throw400UsernameNotFound() throws Exception {
        // api: GET /api/v1/profile/me ==> 400 : UsernameNotFound
        String path = ApiConfig.Profile.GET_ME;

        when(profileService.getMe()).thenThrow(new UsernameNotFoundException());

        String response = mockMvc
            .perform(get(path))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorResponse expected = new ErrorResponse(
            HttpStatus.BAD_REQUEST,
            ErrorMessageConfig.USERNAME_NOT_FOUND,
            null,
            path);

        ErrorResponse actual = objectMapper.readValue(response, ErrorResponse.class);

        assertEquals(expected, actual);
        verify(profileService, times(1)).getMe();
    }

    @Test
    void ProfileController_UpdateMe_Return204NoContent() throws Exception {
        // api: PUT /api/v1/profile/me ==> 204 : No Content
        String path = ApiConfig.Profile.UPDATE_ME;

        UpdateProfileInfoRequest request = new UpdateProfileInfoRequest(
            "name",
            "bio",
            "location");

        String body = objectMapper.writeValueAsString(request);

        doNothing().when(profileService).updateMeProfileInfo(request);

        mockMvc
            .perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andExpect(status().isNoContent());

        verify(profileService, times(1)).updateMeProfileInfo(request);
    }

    @Test
    void ProfileController_UpdateMe_Throw400InvalidRequest_NameExceeds50Characters() throws Exception {
        // api: PUT /api/v1/profile/me ==> 400 : Invalid Request
        String path = ApiConfig.Profile.UPDATE_ME;

        UpdateProfileInfoRequest request = new UpdateProfileInfoRequest(
            "ThisNameIsTooLongBy......................1Character",
            "bio",
            "location");

        String body = objectMapper.writeValueAsString(request);

        doNothing().when(profileService).updateMeProfileInfo(request);

        String response = mockMvc
            .perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorResponse expected = new ErrorResponse(
            HttpStatus.BAD_REQUEST,
            ErrorMessageConfig.INVALID_REQUEST,
            "Name must not exceed 50 characters.",
            path);

        ErrorResponse actual = objectMapper.readValue(response, ErrorResponse.class);

        assertEquals(expected, actual);
        verify(profileService, never()).updateMeProfileInfo(request);
    }

    @Test
    void ProfileController_UpdateMe_Throw400InvalidRequest_BioExceeds160Characters() throws Exception {
        // api: PUT /api/v1/profile/me ==> 400 : Invalid Request
        String path = ApiConfig.Profile.UPDATE_ME;

        UpdateProfileInfoRequest request = new UpdateProfileInfoRequest(
            "name",
            "ThisBioIsTooLongBy.....................................................................................................................................1Character",
            "location");

        String body = objectMapper.writeValueAsString(request);

        doNothing().when(profileService).updateMeProfileInfo(request);

        String response = mockMvc
            .perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorResponse expected = new ErrorResponse(
            HttpStatus.BAD_REQUEST,
            ErrorMessageConfig.INVALID_REQUEST,
            "Bio must not exceed 160 characters.",
            path);

        ErrorResponse actual = objectMapper.readValue(response, ErrorResponse.class);

        assertEquals(expected, actual);
        verify(profileService, never()).updateMeProfileInfo(request);
    }

    @Test
    void ProfileController_UpdateMe_Throw400InvalidRequest_LocationExceeds30Characters() throws Exception {
        // api: PUT /api/v1/profile/me ==> 400 : Invalid Request
        String path = ApiConfig.Profile.UPDATE_ME;

        UpdateProfileInfoRequest request = new UpdateProfileInfoRequest(
            "name",
            "bio",
            "ThisLocationIsTooLongBy4Characters");

        String body = objectMapper.writeValueAsString(request);

        doNothing().when(profileService).updateMeProfileInfo(request);

        String response = mockMvc
            .perform(put(path)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ErrorResponse expected = new ErrorResponse(
            HttpStatus.BAD_REQUEST,
            ErrorMessageConfig.INVALID_REQUEST,
            "Location must not exceed 30 characters.",
            path);

        ErrorResponse actual = objectMapper.readValue(response, ErrorResponse.class);

        assertEquals(expected, actual);
        verify(profileService, never()).updateMeProfileInfo(request);
    }

    @Test
    void ProfileController_GetByUsername_ReturnProfileResponse() throws Exception {
        // api: GET /api/v1/profile/{username} ==> 200 : ProfileResponse
        String path = ApiConfig.Profile.GET_BY_USERNAME;

        User user = new User("test", "test");
        Profile profile = new Profile(user);
        ProfileResponse expected = ProfileMapper.toResponse(profile);

        when(profileService.getByUsername(expected.username())).thenReturn(expected);

        String response = mockMvc
            .perform(get(path, expected.username()))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        ProfileResponse actual = objectMapper.readValue(response, ProfileResponse.class);
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

        ErrorResponse expected = new ErrorResponse(
            HttpStatus.BAD_REQUEST,
            ErrorMessageConfig.USERNAME_NOT_FOUND,
            null,
            path);

        ErrorResponse actual = objectMapper.readValue(response, ErrorResponse.class);

        assertEquals(expected, actual);
        verify(profileService, times(1)).getByUsername("non-existent-user");
    }

}

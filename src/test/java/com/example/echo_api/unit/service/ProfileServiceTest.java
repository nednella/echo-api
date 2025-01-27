package com.example.echo_api.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.echo_api.exception.custom.username.UsernameNotFoundException;
import com.example.echo_api.persistence.dto.request.profile.UpdateProfileInfoRequest;
import com.example.echo_api.persistence.dto.response.profile.ProfileResponse;
import com.example.echo_api.persistence.mapper.ProfileMapper;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.model.user.User;
import com.example.echo_api.persistence.repository.ProfileRepository;
import com.example.echo_api.service.profile.ProfileService;
import com.example.echo_api.service.profile.ProfileServiceImpl;
import com.example.echo_api.service.session.SessionService;

/**
 * Unit test class for {@link ProfileService}.
 */
@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private SessionService sessionService;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileServiceImpl profileService;

    /**
     * Test ensures that the {@link ProfileServiceImpl#registerForUser(User)} method
     * correctly creates a new profile for the supplied user.
     */
    @Test
    void ProfileService_RegisterForUser_ReturnProfile() {
        // arrange
        User user = new User("test", "test");
        Profile expected = new Profile(user);

        when(profileRepository.save(any(Profile.class))).thenReturn(expected);

        // act
        Profile actual = profileService.registerForUser(user);

        // assert
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(profileRepository, times(1)).save(any(Profile.class));
    }

    /**
     * Test ensures that the {@link ProfileServiceImpl#getByUsername(String)} method
     * correctly returns the profile when the username exists.
     */
    @Test
    void ProfileService_GetByUsername_ReturnProfileResponse() {
        // arrange
        User user = new User("test", "test");
        Profile profile = new Profile(user);
        ProfileResponse expected = ProfileMapper.toResponse(profile);

        when(profileRepository.findByUsername(profile.getUsername())).thenReturn(Optional.of(profile));

        // act
        ProfileResponse actual = profileService.getByUsername(profile.getUsername());

        // assert
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(profileRepository, times(1)).findByUsername(profile.getUsername());
    }

    /**
     * Test ensures that the {@link ProfileServiceImpl#getByUsername(String)} method
     * correctly throws a {@link UsernameNotFoundException} when no such profile
     * with the supplied username exists.
     */
    @Test
    void ProfileService_GetByUsername_ThrowUsernameNotFound() {
        // arrange
        String username = "non-existent-user";
        when(profileRepository.findByUsername(username)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(UsernameNotFoundException.class, () -> profileService.getByUsername(username));
        verify(profileRepository, times(1)).findByUsername(username);
    }

    /**
     * Test ensures that the {@link ProfileServiceImpl#getMe()} method correctly
     * returns the authenticated user's profile.
     */
    @Test
    void ProfileService_GetMe_ReturnProfileResponse() {
        // arrange
        User user = new User("test", "test");
        Profile profile = new Profile(user);
        ProfileResponse expected = ProfileMapper.toResponse(profile);

        when(sessionService.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(profile));

        // act
        ProfileResponse actual = profileService.getMe();

        // assert
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(profileRepository, times(1)).findByUsername(user.getUsername());
    }

    /**
     * Test ensures that the {@link ProfileServiceImpl#getMe()} method correctly
     * throws a {@link UsernameNotFoundException} when no such profile with the
     * authenticated user's username exists.
     */
    @Test
    void ProfileService_GetMe_ThrowUsernameNotFound() {
        // arrange
        User user = new User("test", "test");

        when(sessionService.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findByUsername(user.getUsername())).thenThrow(new UsernameNotFoundException());

        // act & assert
        assertThrows(UsernameNotFoundException.class, () -> profileService.getMe());
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(profileRepository, times(1)).findByUsername(user.getUsername());
    }

    /**
     * Test ensures that the
     * {@link ProfileServiceImpl#updateMeProfileInfo(UpdateProfileInfoRequest)}
     * method correctly updates the authenticated user's profile information.
     */
    @Test
    void ProfileService_UpdateMeProfileInfo_ReturnVoid() {
        // arrange
        User user = new User("test", "test");
        Profile profile = new Profile(user);
        UpdateProfileInfoRequest request = new UpdateProfileInfoRequest(
            "John Doe",
            "Bio",
            "Location");

        when(sessionService.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(profile));

        // act
        profileService.updateMeProfileInfo(request);

        // assert
        assertEquals(request.name(), profile.getName());
        assertEquals(request.bio(), profile.getBio());
        assertEquals(request.location(), profile.getLocation());
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(profileRepository, times(1)).findByUsername(user.getUsername());
    }

    /**
     * Test ensures that the
     * {@link ProfileServiceImpl#updateMeProfileInfo(UpdateProfileInfoRequest)}
     * method correctly throws a {@link UsernameNotFoundException} when no such
     * profile with the authenticated user's username exists.
     */
    @Test
    void ProfileService_UpdateMeProfileInfo_ThrowUsernameNotFound() {
        // arrange
        User user = new User("test", "test");

        UpdateProfileInfoRequest request = new UpdateProfileInfoRequest(
            "name",
            "bio",
            "location");

        when(sessionService.getAuthenticatedUser()).thenReturn(user);
        when(profileRepository.findByUsername(user.getUsername())).thenThrow(new UsernameNotFoundException());

        // act & assert
        assertThrows(UsernameNotFoundException.class, () -> profileService.updateMeProfileInfo(request));
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(profileRepository, times(1)).findByUsername(user.getUsername());
    }

}

package com.example.echo_api.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.echo_api.exception.custom.username.UsernameNotFoundException;
import com.example.echo_api.persistence.dto.request.profile.UpdateProfileDTO;
import com.example.echo_api.persistence.dto.response.profile.MetricsDTO;
import com.example.echo_api.persistence.dto.response.profile.ProfileDTO;
import com.example.echo_api.persistence.dto.response.profile.RelationshipDTO;
import com.example.echo_api.persistence.mapper.MetricsMapper;
import com.example.echo_api.persistence.mapper.ProfileMapper;
import com.example.echo_api.persistence.model.account.Account;
import com.example.echo_api.persistence.model.profile.Metrics;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.repository.ProfileRepository;
import com.example.echo_api.service.metrics.MetricsService;
import com.example.echo_api.service.profile.ProfileService;
import com.example.echo_api.service.profile.ProfileServiceImpl;
import com.example.echo_api.service.relationship.RelationshipService;
import com.example.echo_api.service.session.SessionService;

/**
 * Unit test class for {@link ProfileService}.
 */
@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private SessionService sessionService;

    @Mock
    private MetricsService metricsService;

    @Mock
    private RelationshipService relationshipService;

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileServiceImpl profileService;

    /**
     * Test ensures that the {@link ProfileServiceImpl#getByUsername(String)} method
     * correctly returns the profile when the username exists.
     */
    @Test
    void ProfileService_GetByUsername_ReturnProfileDTO() {
        // arrange
        Account account = new Account("test", "test");
        Profile profile = new Profile(account);
        Metrics metrics = new Metrics(profile);
        MetricsDTO metricsDto = MetricsMapper.toDTO(metrics);
        RelationshipDTO relationshipDto = new RelationshipDTO(false, false, false, false);
        ProfileDTO expected = ProfileMapper.toDTO(profile, metricsDto, relationshipDto);

        when(sessionService.getAuthenticatedUser()).thenReturn(account);
        when(profileRepository.findByUsername(account.getUsername())).thenReturn(Optional.of(profile));
        when(profileRepository.findByUsername(profile.getUsername())).thenReturn(Optional.of(profile));
        when(metricsService.getMetrics(profile)).thenReturn(metricsDto);
        when(relationshipService.getRelationship(profile, profile)).thenReturn(relationshipDto);

        // act
        ProfileDTO actual = profileService.getByUsername(profile.getUsername());

        // assert
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(profileRepository, times(2)).findByUsername(profile.getUsername());
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
        Account account = new Account("test", "test");
        Profile profile = new Profile(account);
        when(sessionService.getAuthenticatedUser()).thenReturn(account);
        when(profileRepository.findByUsername(account.getUsername())).thenReturn(Optional.of(profile));
        when(profileRepository.findByUsername(username)).thenReturn(Optional.empty());

        // act & assert
        assertThrows(UsernameNotFoundException.class, () -> profileService.getByUsername(username));
        verify(profileRepository, times(1)).findByUsername(username);
    }

    /**
     * Test ensures that the {@link ProfileServiceImpl#getMe()} method correctly
     * returns the authenticated account's profile.
     */
    @Test
    void ProfileService_GetMe_ReturnProfileResponse() {
        // arrange
        Account account = new Account("test", "test");
        Profile profile = new Profile(account);
        Metrics metrics = new Metrics(profile);
        MetricsDTO metricsDto = MetricsMapper.toDTO(metrics);
        ProfileDTO expected = ProfileMapper.toDTO(profile, metricsDto, null);

        when(sessionService.getAuthenticatedUser()).thenReturn(account);
        when(profileRepository.findByUsername(account.getUsername())).thenReturn(Optional.of(profile));
        when(metricsService.getMetrics(profile)).thenReturn(metricsDto);

        // act
        ProfileDTO actual = profileService.getMe();

        // assert
        assertNotNull(actual);
        assertEquals(expected, actual);
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(profileRepository, times(1)).findByUsername(account.getUsername());
    }

    /**
     * Test ensures that the {@link ProfileServiceImpl#getMe()} method correctly
     * throws a {@link UsernameNotFoundException} when no such profile with the
     * authenticated account's username exists.
     */
    @Test
    void ProfileService_GetMe_ThrowUsernameNotFound() {
        // arrange
        Account account = new Account("test", "test");

        when(sessionService.getAuthenticatedUser()).thenReturn(account);
        when(profileRepository.findByUsername(account.getUsername())).thenThrow(new UsernameNotFoundException());

        // act & assert
        assertThrows(UsernameNotFoundException.class, () -> profileService.getMe());
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(profileRepository, times(1)).findByUsername(account.getUsername());
    }

    /**
     * Test ensures that the
     * {@link ProfileServiceImpl#updateMeProfileInfo(UpdateProfileDTO)} method
     * correctly updates the authenticated account's profile information.
     */
    @Test
    void ProfileService_UpdateMeProfileInfo_ReturnVoid() {
        // arrange
        Account account = new Account("test", "test");
        Profile profile = new Profile(account);
        UpdateProfileDTO request = new UpdateProfileDTO(
            "John Doe",
            "Bio",
            "Location");

        when(sessionService.getAuthenticatedUser()).thenReturn(account);
        when(profileRepository.findByUsername(account.getUsername())).thenReturn(Optional.of(profile));

        // act
        profileService.updateMeProfile(request);

        // assert
        assertEquals(request.name(), profile.getName());
        assertEquals(request.bio(), profile.getBio());
        assertEquals(request.location(), profile.getLocation());
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(profileRepository, times(1)).findByUsername(account.getUsername());
    }

    /**
     * Test ensures that the
     * {@link ProfileServiceImpl#updateMeProfileInfo(UpdateProfileDTO)} method
     * correctly throws a {@link UsernameNotFoundException} when no such profile
     * with the authenticated account's username exists.
     */
    @Test
    void ProfileService_UpdateMeProfileInfo_ThrowUsernameNotFound() {
        // arrange
        Account account = new Account("test", "test");

        UpdateProfileDTO request = new UpdateProfileDTO(
            "name",
            "bio",
            "location");

        when(sessionService.getAuthenticatedUser()).thenReturn(account);
        when(profileRepository.findByUsername(account.getUsername())).thenThrow(new UsernameNotFoundException());

        // act & assert
        assertThrows(UsernameNotFoundException.class, () -> profileService.updateMeProfile(request));
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(profileRepository, times(1)).findByUsername(account.getUsername());
    }

    // ---- follow ----

    @Test
    void ProfileService_Follow_Retun204NoContent() {
    }

    @Test
    void ProfileService_Follow_Throw400UsernameNotFound() {
    }

    @Test
    void ProfileService_Follow_ThrowSelfActionException() {
    }

    @Test
    void ProfileService_Follow_ThrowAlreadyFollowingException() {
    }

    @Test
    void ProfileService_Follow_ThrowBlockedException() {
    }

    // ---- unfollow ----

    @Test
    void ProfileService_Unfollow_Retun204NoContent() {
    }

    @Test
    void ProfileService_Unfollow_Throw400UsernameNotFound() {
    }

    @Test
    void ProfileService_Unfollow_ThrowSelfActionException() {
    }

    @Test
    void ProfileService_Unfollow_ThrowNotFollowingException() {
    }

    // ---- block ----

    @Test
    void ProfileService_Block_Retun204NoContent() {
    }

    @Test
    void ProfileService_Block_Throw400UsernameNotFound() {
    }

    @Test
    void ProfileService_Block_ThrowSelfActionException() {
    }

    @Test
    void ProfileService_Block_ThrowAlreadyBlockingException() {
    }

    // ---- unblock ----

    @Test
    void ProfileService_Unblock_Retun204NoContent() {
    }

    @Test
    void ProfileService_Unblock_Throw400UsernameNotFound() {
    }

    @Test
    void ProfileService_Unblock_ThrowSelfActionException() {
    }

    @Test
    void ProfileService_Unblock_ThrowNotBlockingException() {
    }

}

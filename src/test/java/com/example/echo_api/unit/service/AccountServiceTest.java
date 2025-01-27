package com.example.echo_api.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.echo_api.exception.custom.password.IncorrectCurrentPasswordException;
import com.example.echo_api.exception.custom.username.UsernameAlreadyExistsException;
import com.example.echo_api.persistence.dto.request.account.UpdatePasswordRequest;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.model.user.User;
import com.example.echo_api.persistence.repository.UserRepository;
import com.example.echo_api.service.account.AccountService;
import com.example.echo_api.service.account.AccountServiceImpl;
import com.example.echo_api.service.profile.ProfileService;
import com.example.echo_api.service.session.SessionService;

/**
 * Unit test class for {@link AccountService}.
 */
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private ProfileService profileService;

    @Mock
    private SessionService sessionService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountServiceImpl accountService;

    private User testUser;

    /**
     * Sets up a {@link User} object before each test.
     */
    @BeforeEach
    public void initUser() {
        testUser = new User("testUsername", "testPassword");
    }

    /**
     * Test ensures that the {@link AccountServiceImpl#register(String, String)}
     * method correctly creates a new user when the username does not already exist.
     */
    @Test
    void accountService_Register_ReturnVoid() {
        // arrange
        when(userRepository.save(testUser)).thenReturn(testUser);
        when(profileService.registerForUser(testUser)).thenReturn(new Profile(testUser));
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // act
        accountService.register(testUser.getUsername(), testUser.getPassword());

        // assert
        verify(userRepository, times(1)).save(testUser);
        verify(passwordEncoder, times(1)).encode(testUser.getPassword());
    }

    /**
     * Test ensures that the {@link AccountServiceImpl#register(String, String)}
     * method throws a {@link UsernameAlreadyExistsException} when the username
     * already exists.
     */
    @Test
    void accountService_Register_ThrowUsernameAlreadyExists() {
        // arrange
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);

        // act & assert
        assertThrows(UsernameAlreadyExistsException.class,
            () -> accountService.register(testUser.getUsername(), testUser.getPassword()));
        verify(userRepository, times(1)).existsByUsername(testUser.getUsername());
    }

    /**
     * Test ensures that the {@link AccountServiceImpl#isUsernameAvailable(String)}
     * method returns true when the username exists in the repository.
     */
    @Test
    void accountService_IsUsernameAvailable_ReturnTrue() {
        // arrange
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);

        // act
        boolean taken = accountService.isUsernameAvailable(testUser.getUsername());

        // assert
        assertFalse(taken);
        verify(userRepository, times(1)).existsByUsername(testUser.getUsername());
    }

    /**
     * Test ensures that the {@link AccountServiceImpl#existsByUsername(String)}
     * method returns false when the username does not exist in the repository.
     */
    @Test
    void accountService_IsUsernameAvailable_ReturnFalse() {
        // arrange
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);

        // act
        boolean taken = accountService.isUsernameAvailable(testUser.getUsername());

        // assert
        assertTrue(taken);
        verify(userRepository, times(1)).existsByUsername(testUser.getUsername());
    }

    /**
     * Test ensures that the {@link AccountServiceImpl#updateUsername(String)}
     * method succeeds when the username does not already exist.
     */
    @Test
    void accountService_UpdateUsername_Success() {
        // arrange
        when(userRepository.existsByUsername("new_username")).thenReturn(false);
        when(sessionService.getAuthenticatedUser()).thenReturn(testUser);
        when(userRepository.save(testUser)).thenReturn(testUser);

        // act
        accountService.updateUsername("new_username");

        // assert
        assertEquals("new_username", testUser.getUsername());
        verify(userRepository, times(1)).existsByUsername("new_username");
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(userRepository, times(1)).save(testUser);
    }

    /**
     * Test ensures that the {@link AccountServiceImpl#updateUsername(String)}
     * methodthrows a {@link UsernameAlreadyExistsException} when the username
     * already exists.
     */
    @Test
    void accountService_UpdateUsername_ThrowUsernameAlreadyExists() {
        // arrange
        when(userRepository.existsByUsername("new_username")).thenReturn(true);

        // act & assert
        assertThrows(UsernameAlreadyExistsException.class,
            () -> accountService.updateUsername("new_username"));
        verify(userRepository, times(1)).existsByUsername("new_username");
    }

    /**
     * Test ensures that the
     * {@link AccountServiceImpl#updatePassword(String, String)} method succeeds
     * when the request is valid.
     */
    @Test
    void accountService_UpdatePassword_Success() {
        // arrange
        UpdatePasswordRequest request = new UpdatePasswordRequest(
            "current",
            "new",
            "new");

        String oldPassword = testUser.getPassword();

        when(sessionService.getAuthenticatedUser()).thenReturn(testUser);
        when(passwordEncoder.matches("current", oldPassword)).thenReturn(true);
        when(passwordEncoder.encode("new")).thenReturn("encodedPassword");
        when(userRepository.save(testUser)).thenReturn(testUser);

        // act
        accountService.updatePassword(request.currentPassword(), request.newPassword());

        // assert
        assertEquals("encodedPassword", testUser.getPassword());
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(passwordEncoder, times(1)).matches("current", oldPassword);
        verify(passwordEncoder, times(1)).encode("new");
        verify(userRepository, times(1)).save(testUser);
    }

    /**
     * Test ensures that the
     * {@link AccountServiceImpl#updatePassword(String, String)} method throws
     * {@link IncorrectCurrentPasswordException} when the supplied current and the
     * stored existing passwords do not match.
     */
    @Test
    void accountService_UpdatePassword_ThrowIncorrectCurrentPassword() {
        // arrange
        UpdatePasswordRequest request = new UpdatePasswordRequest(
            "wrong_password",
            "new",
            "new");

        when(sessionService.getAuthenticatedUser()).thenReturn(testUser);
        when(passwordEncoder.matches("wrong_password", testUser.getPassword())).thenReturn(false);

        // act & assert
        assertThrows(IncorrectCurrentPasswordException.class,
            () -> accountService.updatePassword(request.currentPassword(), request.newPassword()));
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(passwordEncoder, times(1)).matches("wrong_password", testUser.getPassword());
    }

}

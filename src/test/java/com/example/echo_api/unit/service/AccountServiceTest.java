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
     * This test ensures that the
     * {@link AccountServiceImpl#register(String, String)} method correctly creates
     * a new user when the username does not already exist.
     * 
     * <p>
     * Mocks the {@link UserRepository#save(User)} method to return the saved user.
     * 
     * <p>
     * Mocks the {@link UserRepository#existsByUsername(String)} to return false.
     * 
     * <p>
     * Mocks the {@link PasswordEncoder#encode(CharSequence)} to return the string
     * "encodedPassword".
     */
    @Test
    void accountService_Register_ReturnVoid() {
        when(userRepository.save(testUser)).thenReturn(testUser);
        when(profileService.registerForUser(testUser)).thenReturn(new Profile(testUser));
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        accountService.register(testUser.getUsername(), testUser.getPassword());

        verify(userRepository, times(1)).save(testUser);
        verify(passwordEncoder, times(1)).encode(testUser.getPassword());
    }

    /**
     * This test ensures that the
     * {@link AccountServiceImpl#register(String, String)} method throws a
     * {@link UsernameAlreadyExistsException} when the username already exists.
     * 
     * <p>
     * Mocks the {@link UserRepository#existsByUsername(String)} method to return
     * true.
     */
    @Test
    void accountService_Register_ThrowUsernameAlreadyExists() {
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class,
            () -> accountService.register(testUser.getUsername(), testUser.getPassword()));
        verify(userRepository, times(1)).existsByUsername(testUser.getUsername());
    }

    /**
     * This test ensures that the
     * {@link AccountServiceImpl#isUsernameAvailable(String)} method returns true
     * when the username exists in the repository.
     * 
     * <p>
     * Mocks the {@link UserRepository#existsByUsername(String)} method to return
     * true.
     */
    @Test
    void accountService_IsUsernameAvailable_ReturnTrue() {
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);

        boolean taken = accountService.isUsernameAvailable(testUser.getUsername());

        assertFalse(taken);
        verify(userRepository, times(1)).existsByUsername(testUser.getUsername());
    }

    /**
     * This test ensures that the
     * {@link AccountServiceImpl#existsByUsername(String)} method returns false when
     * the username does not exist in the repository.
     * 
     * <p>
     * Mocks the {@link UserRepository#existsByUsername(String)} method to return
     * false.
     */
    @Test
    void accountService_IsUsernameAvailable_ReturnFalse() {
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);

        boolean taken = accountService.isUsernameAvailable(testUser.getUsername());

        assertTrue(taken);
        verify(userRepository, times(1)).existsByUsername(testUser.getUsername());
    }

    /**
     * This test ensures that the {@link AccountServiceImpl#updateUsername(String)}
     * method succeeds when the username does not already exist.
     * 
     * <p>
     * Mocks the {@link UserRepository#existsByUsername(String)} method to return
     * false.
     * 
     * <p>
     * Mocks the {@link sessionService#getAuthenticatedUser()} method to return
     * {@code testUser}.
     * 
     * <p>
     * Mocks the {@link UserRepository#save(User)} method to return
     * {@code testUser}.
     */
    @Test
    void accountService_UpdateUsername_Success() {
        when(userRepository.existsByUsername("new_username")).thenReturn(false);
        when(sessionService.getAuthenticatedUser()).thenReturn(testUser);
        when(userRepository.save(testUser)).thenReturn(testUser);

        accountService.updateUsername("new_username");

        assertEquals("new_username", testUser.getUsername());
        verify(userRepository, times(1)).existsByUsername("new_username");
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(userRepository, times(1)).save(testUser);
    }

    /**
     * This test ensures that the {@link AccountServiceImpl#updateUsername(String)}
     * methodthrows a {@link UsernameAlreadyExistsException} when the username
     * already exists.
     * 
     * <p>
     * Mocks the {@link UserRepository#existsByUsername(String)} method to return
     * true.
     */
    @Test
    void accountService_UpdateUsername_ThrowUsernameAlreadyExists() {
        when(userRepository.existsByUsername("new_username")).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class,
            () -> accountService.updateUsername("new_username"));
        verify(userRepository, times(1)).existsByUsername("new_username");
    }

    /**
     * This test ensures that the
     * {@link AccountServiceImpl#updatePassword(UpdatePasswordRequest)} method
     * succeeds when the request is valid.
     * 
     * <p>
     * Mocks the {@link AuthenticatedaccountService#getAuthenticatedUser()} method
     * to return {@code testUser}.
     * 
     * <p>
     * Mocks the {@link PasswordEncoder#matches(String, string)} method to return
     * true.
     * 
     * <p>
     * Mocks the {@link PasswordEncoder#encode(String)} method to return
     * "encodedPassword".
     * 
     * <p>
     * Mocks the {@link UserRepository#save(User)} method to return
     * {@code testUser}.
     */
    @Test
    void accountService_UpdatePassword_Success() {
        UpdatePasswordRequest request = new UpdatePasswordRequest(
            "current",
            "new",
            "new");

        String oldPassword = testUser.getPassword();

        when(sessionService.getAuthenticatedUser()).thenReturn(testUser);
        when(passwordEncoder.matches("current", oldPassword)).thenReturn(true);
        when(passwordEncoder.encode("new")).thenReturn("encodedPassword");
        when(userRepository.save(testUser)).thenReturn(testUser);

        accountService.updatePassword(request);

        assertEquals("encodedPassword", testUser.getPassword());
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(passwordEncoder, times(1)).matches("current", oldPassword);
        verify(passwordEncoder, times(1)).encode("new");
        verify(userRepository, times(1)).save(testUser);
    }

    /**
     * This test ensures that the
     * {@link AccountServiceImpl#updatePassword(UpdatePasswordRequest)} method
     * throws {@link IncorrectCurrentPasswordException} when the supplied current
     * and the stored current passwords do not match.
     * 
     * <p>
     * Mocks the {@link AuthenticatedaccountService#getAuthenticatedUser(User)}
     * method to return {@code testUser}.
     * 
     * <p>
     * Mocks the {@link PasswordEncoder#matches(String, String)} method to return
     * false.
     */
    @Test
    void accountService_UpdatePassword_ThrowIncorrectCurrentPassword() {
        UpdatePasswordRequest request = new UpdatePasswordRequest(
            "wrong_password",
            "new",
            "new");

        when(sessionService.getAuthenticatedUser()).thenReturn(testUser);
        when(passwordEncoder.matches("wrong_password", testUser.getPassword())).thenReturn(false);

        assertThrows(IncorrectCurrentPasswordException.class,
            () -> accountService.updatePassword(request));
        verify(sessionService, times(1)).getAuthenticatedUser();
        verify(passwordEncoder, times(1)).matches("wrong_password", testUser.getPassword());
    }

}

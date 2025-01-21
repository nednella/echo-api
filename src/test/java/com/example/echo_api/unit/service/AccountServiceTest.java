package com.example.echo_api.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.echo_api.exception.custom.password.ConfirmationPasswordMismatchException;
import com.example.echo_api.exception.custom.password.IncorrectCurrentPasswordException;
import com.example.echo_api.exception.custom.password.NewPasswordEqualsOldPasswordException;
import com.example.echo_api.exception.custom.username.UsernameAlreadyExistsException;
import com.example.echo_api.exception.custom.username.UsernameNotFoundException;
import com.example.echo_api.persistence.dto.request.account.UpdatePasswordRequest;
import com.example.echo_api.persistence.model.User;
import com.example.echo_api.persistence.repository.UserRepository;
import com.example.echo_api.service.account.AccountService;
import com.example.echo_api.service.account.AccountServiceImpl;
import com.example.echo_api.service.auth.AuthService;

/**
 * Unit test class for {@link AccountService}.
 */
@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AuthService authService;

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
        testUser = new User(
            "testUsername",
            "testPassword");
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
     * This test ensures that the {@link AccountServiceImpl#findAll()} method
     * correctly returns a list of users from the repository.
     * 
     * <p>
     * Mocks the {@link UserRepository#findAll()} method to return a list of users.
     */
    @Test
    void accountService_FindAll_ReturnListOfUser() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        List<User> users = accountService.findAll();

        assertEquals(1, users.size());
        assertIterableEquals(List.of(testUser), users);
        verify(userRepository, times(1)).findAll();
    }

    /**
     * This test ensures that the {@link AccountServiceImpl#findAll()} method
     * correctly returns an empty list when no users exist.
     * 
     * <p>
     * Mocks the {@link UserRepository#findAll()} method to return an empty list.
     */
    @Test
    void accountService_FindAll_ReturnListOfEmpty() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> users = accountService.findAll();

        assertEquals(0, users.size());
        assertIterableEquals(List.of(), users);
        verify(userRepository, times(1)).findAll();
    }

    /**
     * This test ensures that the {@link AccountServiceImpl#findByUsername(String)}
     * method correctly returns the user when the username exists.
     * 
     * <p>
     * Mocks the {@link UserRepository#findByUsername(String)} method to return a
     * user.
     */
    @Test
    void accountService_FindByUsername_ReturnUser() {
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        User foundUser = accountService.findByUsername(testUser.getUsername());

        assertEquals(testUser, foundUser);
        verify(userRepository, times(1)).findByUsername(testUser.getUsername());
    }

    /**
     * This test ensures that the {@link AccountServiceImpl#findByUsername(String)}
     * method throws a {@link UsernameNotFoundException} when the username does not
     * exist.
     * 
     * <p>
     * Mocks the {@link UserRepository#findByUsername(String)} method to throw a
     * {@link UsernameNotFoundException}.
     */
    @Test
    void accountService_FindByUsername_ThrowUsernameNotFound() {
        when(userRepository.findByUsername(testUser.getUsername())).thenThrow(new UsernameNotFoundException());

        assertThrows(UsernameNotFoundException.class,
            () -> accountService.findByUsername(testUser.getUsername()));
        verify(userRepository, times(1)).findByUsername(testUser.getUsername());
    }

    /**
     * This test ensures that the
     * {@link AccountServiceImpl#existsByUsername(String)} method returns true when
     * the username exists in the repository.
     * 
     * <p>
     * Mocks the {@link UserRepository#existsByUsername(String)} method to return
     * true.
     */
    @Test
    void accountService_ExistsByUsername_ReturnTrue() {
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);

        boolean exists = accountService.existsByUsername(testUser.getUsername());

        assertTrue(exists);
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
    void accountService_ExistsByUsername_ReturnFalse() {
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);

        boolean exists = accountService.existsByUsername(testUser.getUsername());

        assertFalse(exists);
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
     * Mocks the {@link AuthService#getAuthenticatedUser()} method to return
     * {@code testUser}.
     * 
     * <p>
     * Mocks the {@link UserRepository#save(User)} method to return
     * {@code testUser}.
     */
    @Test
    void accountService_UpdateUsername_Success() {
        when(userRepository.existsByUsername("new_username")).thenReturn(false);
        when(authService.getAuthenticatedUser()).thenReturn(testUser);
        when(userRepository.save(testUser)).thenReturn(testUser);

        accountService.updateUsername("new_username");

        assertEquals("new_username", testUser.getUsername());
        verify(userRepository, times(1)).existsByUsername("new_username");
        verify(authService, times(1)).getAuthenticatedUser();
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

        when(authService.getAuthenticatedUser()).thenReturn(testUser);
        when(passwordEncoder.matches("current", oldPassword)).thenReturn(true);
        when(passwordEncoder.encode("new")).thenReturn("encodedPassword");
        when(userRepository.save(testUser)).thenReturn(testUser);

        accountService.updatePassword(request);

        assertEquals("encodedPassword", testUser.getPassword());
        verify(authService, times(1)).getAuthenticatedUser();
        verify(passwordEncoder, times(1)).matches("current", oldPassword);
        verify(passwordEncoder, times(1)).encode("new");
        verify(userRepository, times(1)).save(testUser);
    }

    /**
     * This test ensures that the
     * {@link AccountServiceImpl#updatePassword(UpdatePasswordRequest)} method
     * throws {@link ConfirmationPasswordMismatchException} when the supplied new
     * and confirmation passwords do not match.
     */
    @Test
    void accountService_UpdatePassword_ThrowConfirmationPasswordMismatch() {
        UpdatePasswordRequest request = new UpdatePasswordRequest(
            "current",
            "new",
            "confirmation");

        assertThrows(ConfirmationPasswordMismatchException.class,
            () -> accountService.updatePassword(request));
    }

    /**
     * This test ensures that the
     * {@link AccountServiceImpl#updatePassword(UpdatePasswordRequest)} method
     * throws {@link NewPasswordEqualsOldPasswordException} when the supplied
     * current and supplied new password match.
     */
    @Test
    void accountService_UpdatePassword_ThrowNewPasswordEqualsOldPassword() {
        UpdatePasswordRequest request = new UpdatePasswordRequest(
            "new",
            "new",
            "new");

        assertThrows(NewPasswordEqualsOldPasswordException.class,
            () -> accountService.updatePassword(request));
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

        when(authService.getAuthenticatedUser()).thenReturn(testUser);
        when(passwordEncoder.matches("wrong_password", testUser.getPassword())).thenReturn(false);

        assertThrows(IncorrectCurrentPasswordException.class,
            () -> accountService.updatePassword(request));
        verify(authService, times(1)).getAuthenticatedUser();
        verify(passwordEncoder, times(1)).matches("wrong_password", testUser.getPassword());
    }

}

package com.example.echo_api.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import com.example.echo_api.exception.custom.UsernameAlreadyExistsException;
import com.example.echo_api.persistence.model.User;
import com.example.echo_api.service.auth.AuthService;
import com.example.echo_api.service.auth.AuthServiceImpl;
import com.example.echo_api.service.user.UserService;

/**
 * Unit test class for {@link AuthService}.
 */
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    private User testUser;
    private UsernamePasswordAuthenticationToken token;

    /**
     * Sets up a {@link User} and {@link UsernamePasswordAuthenticationToken} object
     * before each test.
     * 
     */
    @BeforeEach
    public void initVariables() {
        testUser = new User(
            "testUsername",
            "testPassword");

        token = new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword());
    }

    /**
     * This test ensures that the {@link AuthService#signIn(String, String)} method
     * does not throw any exceptions.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to return an
     * authenticated token.
     * 
     */
    @Test
    public void AuthService_SignIn_ReturnVoid() {
        when(authenticationManager.authenticate(token)).thenReturn(token);

        assertDoesNotThrow(() -> authService.signIn(testUser.getUsername(), testUser.getPassword()));
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#signIn(String, String)} method
     * throws {@link DisabledException}.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to throw
     * {@link DisabledException}.
     * 
     */
    @Test
    public void AuthService_SignIn_ThrowDisabledException() {
        when(authenticationManager.authenticate(token)).thenThrow(new DisabledException(""));

        assertThrows(DisabledException.class,
            () -> authService.signIn(testUser.getUsername(), testUser.getPassword()));
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#signIn(String, String)} method
     * throws {@link LockedException}.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to throw
     * {@link LockedException}.
     * 
     */
    @Test
    public void AuthService_SignIn_ThrowLockedException() {
        when(authenticationManager.authenticate(token)).thenThrow(new LockedException(""));

        assertThrows(LockedException.class,
            () -> authService.signIn(testUser.getUsername(), testUser.getPassword()));
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#signIn(String, String)} method
     * throws {@link BadCredentialsException}.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to throw
     * {@link BadCredentialsException}.
     * 
     */
    @Test
    public void AuthService_SignIn_ThrowBadCredentialsException() {
        when(authenticationManager.authenticate(token)).thenThrow(new BadCredentialsException(""));

        assertThrows(BadCredentialsException.class,
            () -> authService.signIn(testUser.getUsername(), testUser.getPassword()));
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#signUp(String, String)} method does
     * not throw any exceptions.
     * 
     * <p>
     * Mocks the {@link UserService#createUser(String, String)} method to do
     * nothing.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to return
     * {@link UsernamePasswordAuthenticationToken}.
     * 
     */
    @Test
    public void AuthService_SignUp_ReturnVoid() {
        doNothing()
            .when(userService)
            .createUser(testUser.getUsername(), testUser.getPassword());

        when(authenticationManager.authenticate(token))
            .thenReturn(token);

        assertDoesNotThrow(() -> authService.signUp(testUser.getUsername(), testUser.getPassword()));

        verify(userService, times(1)).createUser(testUser.getUsername(), testUser.getPassword());
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#signUp(String, String)} method
     * throws {@link UsernameAlreadyExistsException}.
     * 
     * <p>
     * Mocks the {@link UserService#createUser(String, String)} method to throw
     * {@link UsernameAlreadyExistsException}.
     * 
     */
    @Test
    public void AuthService_SignUp_ThrowUsernameAlreadyExists() {
        doThrow(new UsernameAlreadyExistsException())
            .when(userService)
            .createUser(testUser.getUsername(), testUser.getPassword());

        assertThrows(UsernameAlreadyExistsException.class,
            () -> authService.signUp(testUser.getUsername(), testUser.getPassword()));

        verify(userService, times(1)).createUser(testUser.getUsername(), testUser.getPassword());
        verify(authenticationManager, never()).authenticate(any());
    }

    /**
     * This test ensures that {@link AuthService#signUp(String, String)} method
     * throws {@link DisabledException}.
     * 
     * <p>
     * Mocks the {@link UserService#createUser(String, String)} method to do
     * nothing.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to throw
     * {@link DisabledException}.
     * 
     */
    @Test
    public void AuthService_SignUp_ThrowDisabledException() {
        doNothing()
            .when(userService)
            .createUser(testUser.getUsername(), testUser.getPassword());

        when(authenticationManager.authenticate(token))
            .thenThrow(new DisabledException(""));

        assertThrows(DisabledException.class,
            () -> authService.signUp(testUser.getUsername(), testUser.getPassword()));

        verify(userService, times(1)).createUser(testUser.getUsername(), testUser.getPassword());
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#signUp(String, String)} method
     * throws {@link LockedException}.
     * 
     * <p>
     * Mocks the {@link UserService#createUser(String, String)} method to do
     * nothing.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to throw
     * {@link LockedException}.
     * 
     */
    @Test
    public void AuthService_SignUp_ThrowLockedException() {
        doNothing()
            .when(userService)
            .createUser(testUser.getUsername(), testUser.getPassword());

        when(authenticationManager.authenticate(token))
            .thenThrow(new LockedException(""));

        assertThrows(LockedException.class,
            () -> authService.signUp(testUser.getUsername(), testUser.getPassword()));

        verify(userService, times(1)).createUser(testUser.getUsername(), testUser.getPassword());
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#signUp(String, String)} method
     * throws {@link BadCredentialsException}.
     * 
     * <p>
     * Mocks the {@link UserService#createUser(String, String)} method to do
     * nothing.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to throw
     * {@link BadCredentialsException}.
     * 
     */
    @Test
    public void AuthService_SignUp_ThrowBadCredentialsException() {
        doNothing()
            .when(userService)
            .createUser(testUser.getUsername(), testUser.getPassword());

        when(authenticationManager.authenticate(token))
            .thenThrow(new BadCredentialsException(""));

        assertThrows(BadCredentialsException.class,
            () -> authService.signUp(testUser.getUsername(), testUser.getPassword()));

        verify(userService, times(1)).createUser(testUser.getUsername(), testUser.getPassword());
        verify(authenticationManager, times(1)).authenticate(token);
    }

}

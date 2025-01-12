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
        // arrange mock behaviour
        when(authenticationManager.authenticate(token)).thenReturn(token);

        // act & assert
        assertDoesNotThrow(() -> authService.signIn(testUser.getUsername(), testUser.getPassword()));

        // verify
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
        // arrange mock behaviour
        when(authenticationManager.authenticate(token)).thenThrow(DisabledException.class);

        // act & assert
        assertThrows(DisabledException.class,
                () -> authService.signIn(testUser.getUsername(), testUser.getPassword()));

        // verify
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
        // arrange mock behaviour
        when(authenticationManager.authenticate(token)).thenThrow(LockedException.class);

        // act & assert
        assertThrows(LockedException.class,
                () -> authService.signIn(testUser.getUsername(), testUser.getPassword()));

        // verify
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
        // arrange mock behaviour
        when(authenticationManager.authenticate(token)).thenThrow(BadCredentialsException.class);

        // act & assert
        assertThrows(BadCredentialsException.class,
                () -> authService.signIn(testUser.getUsername(), testUser.getPassword()));

        // verify
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#signUp(String, String)} method
     * does not throw any exceptions.
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
        // arrange mock behaviour
        doNothing()
                .when(userService)
                .createUser(testUser.getUsername(), testUser.getPassword());
        when(authenticationManager.authenticate(token))
                .thenReturn(token);

        // act & assert
        assertDoesNotThrow(() -> authService.signUp(testUser.getUsername(), testUser.getPassword()));

        // verify
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
        // arrange mock behaviour
        doThrow(UsernameAlreadyExistsException.class)
                .when(userService)
                .createUser(testUser.getUsername(), testUser.getPassword());

        // act & assert
        assertThrows(UsernameAlreadyExistsException.class,
                () -> authService.signUp(testUser.getUsername(), testUser.getPassword()));

        // verify
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
        // arrange mock behaviour
        doNothing()
                .when(userService)
                .createUser(testUser.getUsername(), testUser.getPassword());
        when(authenticationManager.authenticate(token))
                .thenThrow(DisabledException.class);

        // act & assert
        assertThrows(DisabledException.class,
                () -> authService.signUp(testUser.getUsername(), testUser.getPassword()));

        // verify
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
        // arrange mock behaviour
        doNothing()
                .when(userService)
                .createUser(testUser.getUsername(), testUser.getPassword());
        when(authenticationManager.authenticate(token))
                .thenThrow(LockedException.class);

        // act & assert
        assertThrows(LockedException.class,
                () -> authService.signUp(testUser.getUsername(), testUser.getPassword()));

        // verify
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
        // arrange mock behaviour
        doNothing()
                .when(userService)
                .createUser(testUser.getUsername(), testUser.getPassword());
        when(authenticationManager.authenticate(token))
                .thenThrow(BadCredentialsException.class);

        // act & assert
        assertThrows(BadCredentialsException.class,
                () -> authService.signUp(testUser.getUsername(), testUser.getPassword()));

        // verify
        verify(userService, times(1)).createUser(testUser.getUsername(), testUser.getPassword());
        verify(authenticationManager, times(1)).authenticate(token);
    }

}

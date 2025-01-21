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

import com.example.echo_api.exception.custom.username.UsernameAlreadyExistsException;
import com.example.echo_api.persistence.dto.request.auth.LoginRequest;
import com.example.echo_api.persistence.dto.request.auth.SignupRequest;
import com.example.echo_api.persistence.model.User;
import com.example.echo_api.service.account.AccountService;
import com.example.echo_api.service.auth.AuthService;
import com.example.echo_api.service.auth.AuthServiceImpl;

/**
 * Unit test class for {@link AuthService}.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AccountService accountService;

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
        testUser = new User("testUsername", "testPassword");
        token = new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword());
    }

    /**
     * This test ensures that the {@link AuthService#login(String, String)} method
     * does not throw any exceptions.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to return an
     * authenticated token.
     * 
     */
    @Test
    void AuthService_Login_ReturnVoid() {
        LoginRequest login = new LoginRequest(testUser.getUsername(), testUser.getPassword());

        when(authenticationManager.authenticate(token)).thenReturn(token);

        assertDoesNotThrow(() -> authService.login(login));
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#login(String, String)} method
     * throws {@link DisabledException}.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to throw
     * {@link DisabledException}.
     * 
     */
    @Test
    void AuthService_Login_ThrowDisabledException() {
        LoginRequest login = new LoginRequest(testUser.getUsername(), testUser.getPassword());

        when(authenticationManager.authenticate(token)).thenThrow(new DisabledException(""));

        assertThrows(DisabledException.class, () -> authService.login(login));
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#login(String, String)} method
     * throws {@link LockedException}.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to throw
     * {@link LockedException}.
     * 
     */
    @Test
    void AuthService_Login_ThrowLockedException() {
        LoginRequest login = new LoginRequest(testUser.getUsername(), testUser.getPassword());

        when(authenticationManager.authenticate(token)).thenThrow(new LockedException(""));

        assertThrows(LockedException.class, () -> authService.login(login));
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#login(String, String)} method
     * throws {@link BadCredentialsException}.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to throw
     * {@link BadCredentialsException}.
     * 
     */
    @Test
    void AuthService_Login_ThrowBadCredentialsException() {
        LoginRequest login = new LoginRequest(testUser.getUsername(), testUser.getPassword());

        when(authenticationManager.authenticate(token)).thenThrow(new BadCredentialsException(""));

        assertThrows(BadCredentialsException.class, () -> authService.login(login));
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#signup(String, String)} method does
     * not throw any exceptions.
     * 
     * <p>
     * Mocks the {@link AccountService#register(String, String)} method to do
     * nothing.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to return
     * {@link UsernamePasswordAuthenticationToken}.
     * 
     */
    @Test
    void AuthService_Signup_ReturnVoid() {
        SignupRequest signup = new SignupRequest(testUser.getUsername(), testUser.getPassword());

        when(accountService.register(testUser.getUsername(), testUser.getPassword())).thenReturn(testUser);
        when(authenticationManager.authenticate(token)).thenReturn(token);

        assertDoesNotThrow(() -> authService.signup(signup));
        verify(accountService, times(1)).register(testUser.getUsername(), testUser.getPassword());
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#signup(String, String)} method
     * throws {@link UsernameAlreadyExistsException}.
     * 
     * <p>
     * Mocks the {@link AccountService#register(String, String)} method to throw
     * {@link UsernameAlreadyExistsException}.
     * 
     */
    @Test
    void AuthService_Signup_ThrowUsernameAlreadyExists() {
        SignupRequest signup = new SignupRequest(testUser.getUsername(), testUser.getPassword());

        when(accountService.register(testUser.getUsername(), testUser.getPassword()))
            .thenThrow(new UsernameAlreadyExistsException());

        assertThrows(UsernameAlreadyExistsException.class, () -> authService.signup(signup));
        verify(accountService, times(1)).register(testUser.getUsername(), testUser.getPassword());
        verify(authenticationManager, never()).authenticate(any());
    }

    /**
     * This test ensures that {@link AuthService#signup(String, String)} method
     * throws {@link DisabledException}.
     * 
     * <p>
     * Mocks the {@link AccountService#register(String, String)} method to do
     * nothing.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to throw
     * {@link DisabledException}.
     * 
     */
    @Test
    void AuthService_Signup_ThrowDisabledException() {
        SignupRequest signup = new SignupRequest(testUser.getUsername(), testUser.getPassword());

        when(accountService.register(testUser.getUsername(), testUser.getPassword())).thenReturn(testUser);
        when(authenticationManager.authenticate(token)).thenThrow(new DisabledException(""));

        assertThrows(DisabledException.class, () -> authService.signup(signup));

        verify(accountService, times(1)).register(testUser.getUsername(), testUser.getPassword());
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#signup(String, String)} method
     * throws {@link LockedException}.
     * 
     * <p>
     * Mocks the {@link AccountService#register(String, String)} method to do
     * nothing.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to throw
     * {@link LockedException}.
     * 
     */
    @Test
    void AuthService_Signup_ThrowLockedException() {
        SignupRequest signup = new SignupRequest(testUser.getUsername(), testUser.getPassword());

        when(accountService.register(testUser.getUsername(), testUser.getPassword())).thenReturn(testUser);
        when(authenticationManager.authenticate(token)).thenThrow(new LockedException(""));

        assertThrows(LockedException.class, () -> authService.signup(signup));
        verify(accountService, times(1)).register(testUser.getUsername(), testUser.getPassword());
        verify(authenticationManager, times(1)).authenticate(token);
    }

    /**
     * This test ensures that {@link AuthService#signup(String, String)} method
     * throws {@link BadCredentialsException}.
     * 
     * <p>
     * Mocks the {@link AccountService#register(String, String)} method to do
     * nothing.
     * 
     * <p>
     * Mocks the {@link AuthenticationManager#authenticate} method to throw
     * {@link BadCredentialsException}.
     * 
     */
    @Test
    void AuthService_Signup_ThrowBadCredentialsException() {
        SignupRequest signup = new SignupRequest(testUser.getUsername(), testUser.getPassword());

        when(accountService.register(testUser.getUsername(), testUser.getPassword())).thenReturn(testUser);
        when(authenticationManager.authenticate(token)).thenThrow(new BadCredentialsException(""));

        assertThrows(BadCredentialsException.class, () -> authService.signup(signup));
        verify(accountService, times(1)).register(testUser.getUsername(), testUser.getPassword());
        verify(authenticationManager, times(1)).authenticate(token);
    }

}

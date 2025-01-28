package com.example.echo_api.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import com.example.echo_api.exception.custom.username.UsernameAlreadyExistsException;
import com.example.echo_api.persistence.dto.request.auth.LoginDTO;
import com.example.echo_api.persistence.dto.request.auth.SignupDTO;
import com.example.echo_api.persistence.model.account.User;
import com.example.echo_api.service.account.AccountService;
import com.example.echo_api.service.auth.AuthService;
import com.example.echo_api.service.auth.AuthServiceImpl;
import com.example.echo_api.service.session.SessionService;

/**
 * Unit test class for {@link AuthService}.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private SessionService sessionSerivce;

    @InjectMocks
    private AuthServiceImpl authService;

    private static User testUser;

    /**
     * Set up a {@link User} object before each test.
     * 
     */
    @BeforeAll
    static void initVariables() {
        testUser = new User("testUsername", "testPassword");
    }

    /**
     * This test ensures that the {@link AuthService#login(String, String)} method
     * does not throw any exceptions.
     */
    @Test
    void AuthService_Login_ReturnVoid() {
        // arrange
        LoginDTO login = new LoginDTO(testUser.getUsername(), testUser.getPassword());

        doNothing()
            .when(sessionSerivce)
            .authenticate(login.username(), login.password());

        // act & assert
        assertDoesNotThrow(() -> authService.login(login));
        verify(sessionSerivce, times(1)).authenticate(login.username(), login.password());
    }

    /**
     * This test ensures that {@link AuthService#login(String, String)} method
     * throws {@link DisabledException}.
     */
    @Test
    void AuthService_Login_ThrowDisabledException() {
        // arrange
        LoginDTO login = new LoginDTO(testUser.getUsername(), testUser.getPassword());

        doThrow(new DisabledException(""))
            .when(sessionSerivce)
            .authenticate(login.username(), login.password());

        // act & assert
        assertThrows(DisabledException.class, () -> authService.login(login));
        verify(sessionSerivce, times(1)).authenticate(login.username(), login.password());
    }

    /**
     * This test ensures that {@link AuthService#login(String, String)} method
     * throws {@link LockedException}.
     */
    @Test
    void AuthService_Login_ThrowLockedException() {
        // arrange
        LoginDTO login = new LoginDTO(testUser.getUsername(), testUser.getPassword());

        doThrow(new LockedException(""))
            .when(sessionSerivce)
            .authenticate(login.username(), login.password());

        // act & assert
        assertThrows(LockedException.class, () -> authService.login(login));
        verify(sessionSerivce, times(1)).authenticate(login.username(), login.password());
    }

    /**
     * This test ensures that {@link AuthService#login(String, String)} method
     * throws {@link BadCredentialsException}.
     */
    @Test
    void AuthService_Login_ThrowBadCredentialsException() {
        // arrange
        LoginDTO login = new LoginDTO(testUser.getUsername(), testUser.getPassword());

        doThrow(new BadCredentialsException(""))
            .when(sessionSerivce)
            .authenticate(login.username(), login.password());

        // act & assert
        assertThrows(BadCredentialsException.class, () -> authService.login(login));
        verify(sessionSerivce, times(1)).authenticate(login.username(), login.password());
    }

    /**
     * This test ensures that {@link AuthService#signup(String, String)} method does
     * not throw any exceptions.
     */
    @Test
    void AuthService_Signup_ReturnVoid() {
        // arrange
        SignupDTO signup = new SignupDTO(testUser.getUsername(), testUser.getPassword());

        when(accountService.register(testUser.getUsername(), testUser.getPassword())).thenReturn(testUser);
        doNothing()
            .when(sessionSerivce)
            .authenticate(signup.username(), signup.password());

        // act & assert
        assertDoesNotThrow(() -> authService.signup(signup));
        verify(accountService, times(1)).register(testUser.getUsername(), testUser.getPassword());
        verify(sessionSerivce, times(1)).authenticate(signup.username(), signup.password());
    }

    /**
     * This test ensures that {@link AuthService#signup(String, String)} method
     * throws {@link UsernameAlreadyExistsException}.
     */
    @Test
    void AuthService_Signup_ThrowUsernameAlreadyExists() {
        // arrange
        SignupDTO signup = new SignupDTO(testUser.getUsername(), testUser.getPassword());

        when(accountService.register(testUser.getUsername(), testUser.getPassword()))
            .thenThrow(new UsernameAlreadyExistsException());

        // act & assert
        assertThrows(UsernameAlreadyExistsException.class, () -> authService.signup(signup));
        verify(accountService, times(1)).register(testUser.getUsername(), testUser.getPassword());
        verify(sessionSerivce, never()).authenticate(signup.username(), signup.password());
    }

    /**
     * This test ensures that {@link AuthService#signup(String, String)} method
     * throws {@link DisabledException}.
     */
    @Test
    void AuthService_Signup_ThrowDisabledException() {
        // arrange
        SignupDTO signup = new SignupDTO(testUser.getUsername(), testUser.getPassword());

        when(accountService.register(testUser.getUsername(), testUser.getPassword())).thenReturn(testUser);
        doThrow(new DisabledException(""))
            .when(sessionSerivce)
            .authenticate(signup.username(), signup.password());

        // act & assert
        assertThrows(DisabledException.class, () -> authService.signup(signup));
        verify(accountService, times(1)).register(testUser.getUsername(), testUser.getPassword());
        verify(sessionSerivce, times(1)).authenticate(signup.username(), signup.password());
    }

    /**
     * This test ensures that {@link AuthService#signup(String, String)} method
     * throws {@link LockedException}.
     */
    @Test
    void AuthService_Signup_ThrowLockedException() {
        // arrange
        SignupDTO signup = new SignupDTO(testUser.getUsername(), testUser.getPassword());

        when(accountService.register(testUser.getUsername(), testUser.getPassword())).thenReturn(testUser);
        doThrow(new LockedException(""))
            .when(sessionSerivce)
            .authenticate(signup.username(), signup.password());

        // act & assert
        assertThrows(LockedException.class, () -> authService.signup(signup));
        verify(accountService, times(1)).register(testUser.getUsername(), testUser.getPassword());
        verify(sessionSerivce, times(1)).authenticate(signup.username(), signup.password());
    }

    /**
     * This test ensures that {@link AuthService#signup(String, String)} method
     * throws {@link BadCredentialsException}.
     */
    @Test
    void AuthService_Signup_ThrowBadCredentialsException() {
        // arrange
        SignupDTO signup = new SignupDTO(testUser.getUsername(), testUser.getPassword());

        when(accountService.register(testUser.getUsername(), testUser.getPassword())).thenReturn(testUser);
        doThrow(new BadCredentialsException(""))
            .when(sessionSerivce)
            .authenticate(signup.username(), signup.password());

        // act & assert
        assertThrows(BadCredentialsException.class, () -> authService.signup(signup));
        verify(accountService, times(1)).register(testUser.getUsername(), testUser.getPassword());
        verify(sessionSerivce, times(1)).authenticate(signup.username(), signup.password());
    }

}

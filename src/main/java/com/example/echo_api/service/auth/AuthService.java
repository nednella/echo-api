package com.example.echo_api.service.auth;

import org.springframework.security.core.AuthenticationException;

import com.example.echo_api.exception.custom.username.UsernameAlreadyExistsException;
import com.example.echo_api.persistence.dto.request.auth.LoginRequest;
import com.example.echo_api.persistence.dto.request.auth.SignupRequest;

public interface AuthService {

    /**
     * Authenticates the supplied credentials.
     * 
     * @param login The login request containing the credentials.
     * @throws AuthenticationException If the authentication fails.
     */
    public void login(LoginRequest login) throws AuthenticationException;

    /**
     * Registes and authenticates the supplied credentials.
     * 
     * @param signup The signup request containing the credentials.
     * @throws UsernameAlreadyExistsException If the supplied username is already
     *                                        taken.
     * @throws AuthenticationException        If the authentication fails.
     */
    public void signup(SignupRequest signup) throws UsernameAlreadyExistsException, AuthenticationException;

}

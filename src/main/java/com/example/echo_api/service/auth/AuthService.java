package com.example.echo_api.service.auth;

import org.springframework.security.core.AuthenticationException;

import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.persistence.dto.request.auth.LoginRequest;
import com.example.echo_api.persistence.dto.request.auth.SignupRequest;

public interface AuthService {

    public void login(LoginRequest login) throws AuthenticationException;

    public void signup(SignupRequest signup) throws UsernameException, AuthenticationException;

}

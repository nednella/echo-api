package com.example.echo_api.service.auth;

import org.springframework.security.core.AuthenticationException;

import com.example.echo_api.exception.custom.username.UsernameException;

public interface AuthService {

    public void signIn(String username, String password) throws AuthenticationException;

    public void signUp(String username, String password) throws UsernameException, AuthenticationException;

}

package com.example.echo_api.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.persistence.dto.request.auth.LoginRequest;
import com.example.echo_api.persistence.dto.request.auth.SignupRequest;
import com.example.echo_api.service.account.AccountService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountService accountService;
    private final AuthenticationManager contextAwareAuthenticationManager;

    @Override
    public void login(LoginRequest login) throws AuthenticationException {
        authenticate(login.username(), login.password());
    }

    @Override
    public void signup(SignupRequest signup) throws UsernameException, AuthenticationException {
        accountService.register(signup.username(), signup.password());
        authenticate(signup.username(), signup.password());
    }

    private void authenticate(String username, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
            .unauthenticated(username, password);
        contextAwareAuthenticationManager.authenticate(token);
    }

}

package com.example.echo_api.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.service.account.AccountService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountService accountService;
    private final AuthenticationManager contextAwareAuthenticationManager;

    @Override
    public void signIn(String username, String password) throws AuthenticationException {
        authenticate(username, password);
    }

    @Override
    public void signUp(String username, String password) throws UsernameException, AuthenticationException {
        accountService.register(username, password);
        authenticate(username, password);
    }

    private void authenticate(String username, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
            .unauthenticated(username, password);
        contextAwareAuthenticationManager.authenticate(token);
    }

}

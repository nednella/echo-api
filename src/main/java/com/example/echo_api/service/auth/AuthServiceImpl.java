package com.example.echo_api.service.auth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.persistence.dto.request.auth.LoginRequest;
import com.example.echo_api.persistence.dto.request.auth.SignupRequest;
import com.example.echo_api.service.account.AccountService;
import com.example.echo_api.service.session.SessionService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountService accountService;
    private final SessionService sessionService;

    @Override
    public void login(LoginRequest login) throws AuthenticationException {
        sessionService.authenticate(login.username(), login.password());
    }

    @Override
    public void signup(SignupRequest signup) throws UsernameException, AuthenticationException {
        accountService.register(signup.username(), signup.password());
        sessionService.authenticate(signup.username(), signup.password());
    }

}

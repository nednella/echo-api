package com.example.echo_api.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.example.echo_api.exception.custom.UsernameAlreadyExistsException;
import com.example.echo_api.service.user.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final AuthenticationManager contextAwareAuthenticationManager;

    @Override
    public void signIn(String username, String password) throws AuthenticationException {
        authenticate(username, password);
    }

    @Override
    public void signUp(String username, String password)
        throws UsernameAlreadyExistsException, AuthenticationException {
        userService.createUser(username, password);
        authenticate(username, password);
    }

    private void authenticate(String username, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
            .unauthenticated(username, password);
        contextAwareAuthenticationManager.authenticate(token);
    }

}

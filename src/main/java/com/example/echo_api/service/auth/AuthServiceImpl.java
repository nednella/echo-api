package com.example.echo_api.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.echo_api.dto.request.SignInRequest;
import com.example.echo_api.dto.request.SignUpReqest;
import com.example.echo_api.exception.user.UserAlreadyExistsException;
import com.example.echo_api.exception.user.UserNotFoundException;
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
    public void signIn(SignInRequest login) throws UserNotFoundException {
        authenticate(login.getUsername(), login.getPassword());
    }

    @Override
    public void signUp(SignUpReqest signup) throws UserAlreadyExistsException {
        userService.createUser(signup.getUsername(), signup.getPassword());
        authenticate(signup.getUsername(), signup.getPassword());
    }

    private void authenticate(String username, String password) {
        try {
            UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                    .unauthenticated(username, password);

            contextAwareAuthenticationManager.authenticate(token);
        } catch (UserNotFoundException e) {
            // TODO: test/handle
        } catch (DisabledException e) {
            // TODO: test/handle
        } catch (LockedException e) {
            // TODO: test/handle
        } catch (BadCredentialsException e) {
            // TODO: test/handle
        }
    }

}

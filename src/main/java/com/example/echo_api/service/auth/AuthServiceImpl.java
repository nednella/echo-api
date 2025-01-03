package com.example.echo_api.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.echo_api.dto.request.SignInRequest;
import com.example.echo_api.dto.request.SignUpReqest;
import com.example.echo_api.exception.user.UserAlreadyExistsException;
import com.example.echo_api.exception.user.UserNotFoundException;
import com.example.echo_api.model.User;
import com.example.echo_api.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    @Override
    public void signIn(SignInRequest signInRequest) {
        try {
            Authentication authRequest = new UsernamePasswordAuthenticationToken(signInRequest.getUsername(),
                    signInRequest.getPassword());
            Authentication authResponse = authManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authResponse);
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

    @Override
    public void signUp(SignUpReqest signUpRequest) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UserAlreadyExistsException(signUpRequest.getUsername());
        }

        User user = new User(
                signUpRequest.getUsername(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);
    }

}

package com.example.echo_api.service.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.exception.custom.username.UsernameNotFoundException;
import com.example.echo_api.persistence.model.SecurityUser;
import com.example.echo_api.persistence.model.User;
import com.example.echo_api.persistence.repository.UserRepository;
import com.example.echo_api.service.account.AccountService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AccountService accountService;
    private final UserRepository userRepository;
    private final AuthenticationManager contextAwareAuthenticationManager;

    @Override
    public void signIn(String username, String password) throws AuthenticationException {
        authenticate(username, password);
    }

    @Override
    public void signUp(String username, String password) throws UsernameException, AuthenticationException {
        accountService.createUser(username, password);
        authenticate(username, password);
    }

    private void authenticate(String username, String password) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
            .unauthenticated(username, password);
        contextAwareAuthenticationManager.authenticate(token);
    }

    /**
     * Retrieves the currently authenticated user from the security context.
     * 
     * <p>
     * This method accesses the Spring Security context to retrieve the
     * authentication information and then extracts the {@link User} entity
     * associated to the authentication.
     *
     * @return the {@link User} entity of the authenticated user, or {@code null} if
     *         no user is authenticated
     */
    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        // TODO: implement a fix

        // Temporary fix: fetch from db to ensure fresh information is returned.
        // Bug: the authenticated session is not updated when user details change.
        User authenticatedUser = ((SecurityUser) authentication.getPrincipal()).getUser();
        return userRepository.findByUsername(authenticatedUser.getUsername())
            .orElseThrow(UsernameNotFoundException::new);
    }

}

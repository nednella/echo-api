package com.example.echo_api.service.session;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.echo_api.exception.custom.username.UsernameNotFoundException;
import com.example.echo_api.persistence.model.SecurityUser;
import com.example.echo_api.persistence.model.User;
import com.example.echo_api.persistence.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final UserRepository userRepository;

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

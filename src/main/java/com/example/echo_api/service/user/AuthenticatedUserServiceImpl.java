package com.example.echo_api.service.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.echo_api.persistence.model.SecurityUser;
import com.example.echo_api.persistence.model.User;

@Service
public class AuthenticatedUserServiceImpl implements AuthenticatedUserService {

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

        return ((SecurityUser) authentication.getPrincipal()).getUser();
    }

}

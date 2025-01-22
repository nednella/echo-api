package com.example.echo_api.service.session;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.echo_api.persistence.model.SecurityUser;
import com.example.echo_api.persistence.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing the session and authentication state of a
 * {@link User}.
 * 
 * @see AuthenticationManager
 * @see SecurityContextRepository
 * @see SecurityContextHolderStrategy
 */
@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
        .getContextHolderStrategy();

    /**
     * Retrieves the authenticated {@link User} entity from
     * {@link SecurityContextHolder}.
     *
     * @return the authenticated {@link User}, or {@code null} if no user is
     *         authenticated
     */
    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = securityContextHolderStrategy.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return ((SecurityUser) authentication.getPrincipal()).getUser();
    }

    /**
     * Custom authentication method, required when manually authenticating users
     * through a custom {@code /login} API endpoint.
     * 
     * <p>
     * By default, Spring Security automatically handles {@link SecurityContext}
     * persistence during form-based authentication via
     * {@link UsernamePasswordAuthenticationFilter}. When implementing a custom
     * login endpoint, that persistence is bypassed, which this method aims to
     * reintroduce.
     * 
     * <p>
     * The method attempts to authenticate the supplied credentials against the
     * database using {@link AuthenticationManager}, and stores the authenticated
     * {@link SecurityContext} in the HTTP session if successful.
     * 
     * <p>
     * For more information, refer to:
     * <ul>
     * <li>Manual authentication:
     * {@link https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html#store-authentication-manually}.
     * <li>CreateEmptyContext:
     * {@link https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html#servlet-authentication-securitycontextholder}
     * <li>SecurityContextHolderStrategy:
     * {@link https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html#use-securitycontextholderstrategy}
     * </ul>
     * 
     * @param username the username of the user to authenticate
     * @param password the password of the user to authenticate
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public void authenticate(String username, String password) throws AuthenticationException {
        // create authentication token
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
            .unauthenticated(username, password);

        // authenticate provided token (compares supplied credentials to db)
        Authentication result = authenticationManager.authenticate(token);

        // set authenticated token in a fresh security context
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(result);
        securityContextHolderStrategy.setContext(context);

        // retrieve current http request/response
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
            .currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder
            .currentRequestAttributes()).getResponse();

        // save security context to the http session for persistence
        securityContextRepository.saveContext(context, request, response);
    }

}

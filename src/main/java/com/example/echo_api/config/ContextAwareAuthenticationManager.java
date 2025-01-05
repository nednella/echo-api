package com.example.echo_api.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.echo_api.service.auth.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Custom {@link AuthenticationManager} class to be consumed during the
 * authentication process.
 * 
 * <p>
 * The class is designed to abstract away the logic that is required to be
 * implemented when replacing the default Spring Security {@code /login} form
 * with a custom login endpoint as part of an API.
 * 
 * <p>
 * By default, Spring Security automatically manages {@link SecurityContext}
 * persistence during form-based authentication via filters, specifically
 * {@link UsernamePasswordAuthenticationFilter} that implements
 * {@link AbstractAuthenticationProcessingFilter}.
 * However, when implementing a custom authentication endpoint, the above
 * process is bypassed. This class reintroduces the missing persistence logic,
 * abstracting it away from the {@link AuthService} to maintain cleaner code.
 * 
 * <p>
 * For more information, refer to:
 * {@link https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html#store-authentication-manually}.
 * 
 */
@RequiredArgsConstructor
public class ContextAwareAuthenticationManager implements AuthenticationManager {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // authenticate the provided credentials in the default manner
        Authentication result = authenticationManager.authenticate(authentication);

        // set the authenticated token in context
        SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(result);
        securityContextHolderStrategy.setContext(context);

        // retrieve current http request and response
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getResponse();

        // save the context to the http session for persistence
        securityContextRepository.saveContext(context, request, response);

        return result;
    }

}

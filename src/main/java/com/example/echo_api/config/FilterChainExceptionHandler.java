package com.example.echo_api.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FilterChainExceptionHandler extends OncePerRequestFilter {

    private HandlerExceptionResolver resolver;

    public FilterChainExceptionHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.debug("Handling Spring Security filter chain exception: {}", ex.getMessage());

            /**
             * Replace any FORBIDDEN exception with UNAUTHORISED exception for
             * unauthenticated users.
             * 
             * NOTE: Spring Security creates anonymous authentication objects for
             * unauthenticated users, which are rejected on the grounds of not having
             * permissions to access protected endpoints, and not on the grounds of being
             * unauthenticated.
             * 
             * For more information, refer to:
             * https://docs.spring.io/spring-security/reference/servlet/authentication/anonymous.html
             * 
             */
            if (ex instanceof AuthorizationDeniedException) {
                ex = new InsufficientAuthenticationException("unauthorised request");
            }

            /** Delegate filter chain exceptions to the global controller advice handlers */
            resolver.resolveException(request, response, null, ex);
        }
    }
}

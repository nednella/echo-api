package com.example.echo_api.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.echo_api.config.ErrorMessageConfig;
import com.example.echo_api.exception.AbstractControllerAdvice;
import com.example.echo_api.persistence.dto.response.error.ErrorDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = AuthController.class)
public class AuthControllerAdvice extends AbstractControllerAdvice {

    @ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
    ResponseEntity<ErrorDTO> handleAuthenticationException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
            request,
            HttpStatus.BAD_REQUEST,
            ErrorMessageConfig.USERNAME_OR_PASSWORD_IS_INCORRECT,
            null);
    }

    @ExceptionHandler({ AccountStatusException.class })
    ResponseEntity<ErrorDTO> handleAccountStatusException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
            request,
            HttpStatus.UNAUTHORIZED,
            ErrorMessageConfig.ACCOUNT_STATUS,
            ex.getMessage());
    }

}
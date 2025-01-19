package com.example.echo_api.controller.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.echo_api.exception.AbstractControllerAdvice;
import com.example.echo_api.exception.custom.UsernameAlreadyExistsException;
import com.example.echo_api.persistence.dto.response.error.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = AuthController.class)
public class AuthControllerAdvice extends AbstractControllerAdvice {

    @ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
    ResponseEntity<ErrorResponse> handleAuthenticationException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
            request,
            HttpStatus.BAD_REQUEST,
            "username or password is incorrect",
            null);
    }

    @ExceptionHandler({ UsernameAlreadyExistsException.class })
    ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
            request,
            HttpStatus.BAD_REQUEST,
            "username already exists",
            null);
    }

    @ExceptionHandler({ AccountStatusException.class })
    ResponseEntity<ErrorResponse> handleAccountStatusException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
            request,
            HttpStatus.UNAUTHORIZED,
            "account status is abnormal",
            ex.getMessage().toLowerCase());
    }

}
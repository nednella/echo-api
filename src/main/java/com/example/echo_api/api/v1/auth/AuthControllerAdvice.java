package com.example.echo_api.api.v1.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.echo_api.exception.AbstractControllerAdvice;
import com.example.echo_api.exception.custom.UsernameAlreadyExistsException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = AuthController.class)
public class AuthControllerAdvice extends AbstractControllerAdvice {

    @ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
    ResponseEntity<?> handleAuthenticationException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(request,
                HttpStatus.BAD_REQUEST,
                "username or password is incorrect");
    }

    @ExceptionHandler({ UsernameAlreadyExistsException.class })
    ResponseEntity<?> handleUsernameAlreadyExistsException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(request,
                HttpStatus.BAD_REQUEST,
                "username already exists");
    }

    @ExceptionHandler({ AccountStatusException.class })
    ResponseEntity<?> handleAccountStatusException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(request,
                HttpStatus.UNAUTHORIZED,
                "account is abnormal");
    }

}

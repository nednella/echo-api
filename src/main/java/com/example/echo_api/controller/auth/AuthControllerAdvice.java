package com.example.echo_api.controller.auth;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.echo_api.exception.AbstractControllerAdvice;
import com.example.echo_api.exception.custom.UsernameAlreadyExistsException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = AuthController.class)
public class AuthControllerAdvice extends AbstractControllerAdvice {

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    ResponseEntity<?> handleInvalidRequestException(HttpServletRequest request, MethodArgumentNotValidException ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        // build linked hashmap of MethodArgumentNotValidException field errors to
        // maintain insertion order
        List<Map<String, String>> details = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> {
                    Map<String, String> tmp = new LinkedHashMap<>();
                    tmp.put("field", error.getField());
                    tmp.put("message", error.getDefaultMessage());
                    return tmp;
                })
                .toList();

        return createExceptionHandler(
                request,
                HttpStatus.BAD_REQUEST,
                "request is invalid",
                details);
    }

    @ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
    ResponseEntity<?> handleAuthenticationException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
                request,
                HttpStatus.BAD_REQUEST,
                "username or password is incorrect",
                null);
    }

    @ExceptionHandler({ UsernameAlreadyExistsException.class })
    ResponseEntity<?> handleUsernameAlreadyExistsException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
                request,
                HttpStatus.BAD_REQUEST,
                "username already exists",
                null);
    }

    @ExceptionHandler({ AccountStatusException.class })
    ResponseEntity<?> handleAccountStatusException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
                request,
                HttpStatus.UNAUTHORIZED,
                "account status is abnormal",
                ex.getMessage().toLowerCase());
    }

}
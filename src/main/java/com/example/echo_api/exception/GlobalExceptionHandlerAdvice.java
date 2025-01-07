package com.example.echo_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerAdvice extends AbstractControllerAdvice {

    @ExceptionHandler({ InsufficientAuthenticationException.class })
    ResponseEntity<?> handleInsufficientAuthenticationException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(request,
                HttpStatus.UNAUTHORIZED,
                "unauthorised request");
    }

    @ExceptionHandler({ AccessDeniedException.class })
    ResponseEntity<?> handleAccessDeniedException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(request,
                HttpStatus.FORBIDDEN,
                "access denied");
    }

    @ExceptionHandler({ NoResourceFoundException.class })
    ResponseEntity<?> handleNotFoundException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(request,
                HttpStatus.NOT_FOUND,
                "resource not found");
    }

    @ExceptionHandler({ Exception.class })
    ResponseEntity<?> handleGenericException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "an unexpected error occurred");
    }

}

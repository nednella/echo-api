package com.example.echo_api.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.echo_api.persistence.dto.response.error.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

/**
 * Global exception handler for the application, using {@link ControllerAdvice}
 * to handle exceptions thrown during the request processing lifecycle.
 * 
 * <p>
 * The methods in the class handle global occurrences for common HTTP status
 * codes (401, 403, 404, 500) across all controllers.
 * 
 */
@Slf4j
@ControllerAdvice
public class GlobalControllerAdvice extends AbstractControllerAdvice {

    /** Jakarta Validation Exception */
    @ExceptionHandler({ ConstraintViolationException.class })
    ResponseEntity<ErrorResponse> handleConstraintViolationException(HttpServletRequest request,
        ConstraintViolationException ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        // build linked hashmap of ConstrantViolationException violations to maintain
        // insertion order
        List<Map<String, String>> details = ex
            .getConstraintViolations()
            .stream()
            .map(violation -> {
                Map<String, String> tmp = new LinkedHashMap<>();
                String field = violation.getPropertyPath().toString();
                field = field.substring(field.lastIndexOf('.') + 1);
                tmp.put("field", field);
                tmp.put("message", violation.getMessage());
                return tmp;
            })
            .toList();

        return createExceptionHandler(
            request,
            HttpStatus.BAD_REQUEST,
            "invalid request",
            details);
    }

    /** Jakarta Validation Exception */
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(HttpServletRequest request,
        MethodArgumentNotValidException ex) {
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
            "invalid request",
            details);
    }

    /** 401 */
    @ExceptionHandler({ InsufficientAuthenticationException.class })
    ResponseEntity<ErrorResponse> handleInsufficientAuthenticationException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
            request,
            HttpStatus.UNAUTHORIZED,
            "unauthorised request",
            null);
    }

    /** 403 */
    @ExceptionHandler({ AccessDeniedException.class })
    ResponseEntity<ErrorResponse> handleAccessDeniedException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
            request,
            HttpStatus.FORBIDDEN,
            "access denied",
            null);
    }

    /** 404 */
    @ExceptionHandler({ NoResourceFoundException.class })
    ResponseEntity<ErrorResponse> handleNotFoundException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
            request,
            HttpStatus.NOT_FOUND,
            "resource not found",
            null);
    }

    /** 500 */
    @ExceptionHandler({ Exception.class })
    ResponseEntity<ErrorResponse> handleGenericException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(request,
            HttpStatus.INTERNAL_SERVER_ERROR,
            "an unexpected error occurred",
            ex.getMessage());
    }

}

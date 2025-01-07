package com.example.echo_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

public abstract class AbstractControllerAdvice {

    protected ResponseEntity<?> createExceptionHandler(
            HttpServletRequest request,
            HttpStatus status,
            String message) {

        ErrorBody error = new ErrorBody(
                status,
                message,
                request.getRequestURI());

        return ResponseEntity
                .status(status)
                .body(new ErrorWrapper(error));
    }

}

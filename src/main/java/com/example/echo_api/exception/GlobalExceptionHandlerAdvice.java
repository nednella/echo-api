package com.example.echo_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerAdvice extends AbstractControllerAdvice {

    @ExceptionHandler({ Exception.class })
    ResponseEntity<?> handleGenericException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(request,
                HttpStatus.INTERNAL_SERVER_ERROR,
                "an unexpected error occurred");
    }

}

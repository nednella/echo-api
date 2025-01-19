package com.example.echo_api.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.echo_api.exception.AbstractControllerAdvice;
import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.persistence.dto.response.error.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = UserController.class)
public class UserControllerAdvice extends AbstractControllerAdvice {

    @ExceptionHandler({ UsernameException.class })
    ResponseEntity<ErrorResponse> handleUsernameException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
            request,
            HttpStatus.BAD_REQUEST,
            ex.getMessage(),
            null);
    }

}

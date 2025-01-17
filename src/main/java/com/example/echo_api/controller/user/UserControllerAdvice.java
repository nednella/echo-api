package com.example.echo_api.controller.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.echo_api.exception.AbstractControllerAdvice;
import com.example.echo_api.exception.custom.UsernameAlreadyExistsException;
import com.example.echo_api.persistence.dto.response.error.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = UserController.class)
public class UserControllerAdvice extends AbstractControllerAdvice {

    @ExceptionHandler({ UsernameNotFoundException.class })
    ResponseEntity<ErrorResponse> handleUsernameNotFoundException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
            request,
            HttpStatus.BAD_REQUEST,
            "username not found",
            null);
    }

    // TODO: redundant class. User creation only occurs via AuthControllers
    // so this exception doesnt really need to be handled here
    @ExceptionHandler({ UsernameAlreadyExistsException.class })
    ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(HttpServletRequest request, Exception ex) {
        log.debug("Handling exception: {}", ex.getMessage());

        return createExceptionHandler(
            request,
            HttpStatus.BAD_REQUEST,
            "username already exists",
            null);
    }

}

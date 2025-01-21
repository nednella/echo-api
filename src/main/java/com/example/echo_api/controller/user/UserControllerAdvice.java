package com.example.echo_api.controller.user;

import org.springframework.web.bind.annotation.ControllerAdvice;

import com.example.echo_api.exception.AbstractControllerAdvice;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = UserController.class)
public class UserControllerAdvice extends AbstractControllerAdvice {

}

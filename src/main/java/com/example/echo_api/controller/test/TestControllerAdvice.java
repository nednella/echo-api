package com.example.echo_api.controller.test;

import org.springframework.web.bind.annotation.ControllerAdvice;

import com.example.echo_api.exception.AbstractControllerAdvice;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(assignableTypes = TestController.class)
public class TestControllerAdvice extends AbstractControllerAdvice {

}

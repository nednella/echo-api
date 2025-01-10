package com.example.echo_api.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record ErrorBody(
        String timestamp,
        int status,
        String message,
        Object details,
        String path) {
    public ErrorBody(
            HttpStatus status,
            String message,
            Object details,
            String path) {
        this(Instant.now().toString(),
                status.value(),
                message,
                details,
                path);
    }
}

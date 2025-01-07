package com.example.echo_api.exception;

import java.time.Instant;

import org.springframework.http.HttpStatus;

public record ErrorBody(
        String timestamp,
        int status,
        String message,
        String path) {
    public ErrorBody(HttpStatus status, String message, String path) {
        this(Instant.now().toString(),
                status.value(),
                message,
                path);
    }
}

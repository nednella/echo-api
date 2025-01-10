package com.example.echo_api.persistence.dto.response.error;

import java.time.Instant;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record ErrorResponse(
        String timestamp,
        int status,
        String message,
        Object details,
        String path) {

    public ErrorResponse(
            HttpStatus status,
            String message,
            Object details,
            String path) {
        this(
                Instant.now().toString(),
                status.value(),
                message,
                details,
                path);
    }
}

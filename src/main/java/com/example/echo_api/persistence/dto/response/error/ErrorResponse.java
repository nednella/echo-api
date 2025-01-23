package com.example.echo_api.persistence.dto.response.error;

import java.time.Instant;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public record ErrorResponse(
    String timestamp,
    int status,
    String message,
    String details,
    String path) {

    public ErrorResponse(
        HttpStatus status,
        String message,
        String details,
        String path) {
        this(
            Instant.now().toString(),
            status.value(),
            message,
            details,
            path);
    }

    /**
     * Compares two {@link ErrorResponse} objects by status code, message and
     * details. The timestamp field is ignored.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (this.getClass() != o.getClass())
            return false;

        ErrorResponse that = (ErrorResponse) o;

        return (this.status == that.status &&
            Objects.equals(this.message, that.message) &&
            Objects.equals(this.details, that.details));
    }

}

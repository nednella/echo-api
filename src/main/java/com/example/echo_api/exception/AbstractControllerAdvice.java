package com.example.echo_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Abstract class that provides a method for generating standardised error
 * responses in the event of a thrown exception in the application.
 * 
 * <p>
 * The class should be extended by ControllerAdvice classes that handle
 * controller-specific exceptions and used to map those exceptions to an
 * appropriate HTTP response.
 * 
 */
public abstract class AbstractControllerAdvice {

    /**
     * Creates a custom error response JSON structure, providing uniform responses
     * across the application.
     * 
     * <p>
     * The returned error will follow the structure:
     * 
     * <pre>
     * {
     *      "error": {
     *          "timestamp": ,
     *          "status": ,
     *          "message": ,
     *          "path": ,
     *      }
     * }
     * </pre>
     * 
     * @param request The incoming HTTP request that resulted in the exception.
     * @param status  The HTTP status code to be returned in the response.
     * @param message A message describing the error.
     * @return A {@link ResponseEntity} containing an {@link ErrorWrapper} with the
     *         generated error details.
     * 
     * @see ErrorBody
     * @see ErrorWrapper
     */
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
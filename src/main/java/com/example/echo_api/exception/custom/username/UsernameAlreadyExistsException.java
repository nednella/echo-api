package com.example.echo_api.exception.custom.username;

/**
 * Thrown if a HTTP request is rejected because the supplied username already
 * exists in the db.
 */
public class UsernameAlreadyExistsException extends UsernameException {

    /**
     * Constructs a {@code UsernameAlreadyExistsException} with the specified
     * message.
     */
    public UsernameAlreadyExistsException() {
        super("Username already exists.");
    }

}
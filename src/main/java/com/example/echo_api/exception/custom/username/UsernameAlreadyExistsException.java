package com.example.echo_api.exception.custom.username;

import com.example.echo_api.config.ErrorMessageConfig;

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
        super(ErrorMessageConfig.USERNAME_ARLEADY_EXISTS);
    }

}
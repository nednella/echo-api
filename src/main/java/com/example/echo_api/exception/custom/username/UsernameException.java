package com.example.echo_api.exception.custom.username;

/**
 * Abstract superclass for all exceptions related to a {@link Username} object
 * being invalid for whatever reason.
 */
public abstract class UsernameException extends RuntimeException {

    /**
     * Constructs a {@code PasswordException} with the specified message and no root
     * cause.
     * 
     * @param msg the detail message
     */
    protected UsernameException(String msg) {
        super(msg);
    }

}
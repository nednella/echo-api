package com.example.echo_api.exception.custom.password;

/**
 * Abstract superclass for all exceptions related to a {@link Password} object
 * being invalid for whatever reason.
 */
public abstract class PasswordException extends RuntimeException {

    /**
     * Constructs a {@code PasswordException} with the specified message and no root
     * cause.
     * 
     * @param msg The detail message.
     */
    protected PasswordException(String msg) {
        super(msg);
    }

}

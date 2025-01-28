package com.example.echo_api.exception.custom.account;

/**
 * Abstract superclass for all exceptions related to a {@link Account} object
 * being invalid for whatever reason.
 */
public abstract class AccountException extends RuntimeException {

    /**
     * Constructs a {@code AccountException} with the specified message and no root
     * cause.
     * 
     * @param msg The detail message.
     */
    protected AccountException(String msg) {
        super(msg);
    }

}

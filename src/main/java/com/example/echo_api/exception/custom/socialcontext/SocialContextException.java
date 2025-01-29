package com.example.echo_api.exception.custom.socialcontext;

/**
 * Abstract superclass for all exceptions related to {@link Profile}
 * relationship interactions.
 */
public abstract class SocialContextException extends RuntimeException {

    /**
     * Constructs a {@code SocialContextException} with the specified message and no
     * root cause.
     * 
     * @param msg The detail message.
     */
    protected SocialContextException(String msg) {
        super(msg);
    }

}

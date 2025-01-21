package com.example.echo_api.exception.custom.username;

import com.example.echo_api.config.ErrorMessageConfig;

/**
 * Thrown if a HTTP request is rejected because the supplied username does not
 * exist in the db.
 * 
 * <p>
 * Note that this exception is intended to differ from
 * {@link org.springframework.security.core.userdetails.UsernameNotFoundException}
 * because it is not associated with the authentication flow.
 */
public class UsernameNotFoundException extends UsernameException {

    /**
     * Constructs a {@code UsernameNotFoundException} with the specified message.
     */
    public UsernameNotFoundException() {
        super(ErrorMessageConfig.USERNAME_NOT_FOUND);
    }

}

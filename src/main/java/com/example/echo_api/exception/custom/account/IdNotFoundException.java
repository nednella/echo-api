package com.example.echo_api.exception.custom.account;

import com.example.echo_api.config.ErrorMessageConfig;

/**
 * Thrown if a HTTP request is rejected because the supplied id does not exist
 * in the db.
 */
public class IdNotFoundException extends AccountException {

    /**
     * Constructs a {@code IdNotFoundException} with the specified message.
     */
    public IdNotFoundException() {
        super(ErrorMessageConfig.ID_NOT_FOUND);
    }

}

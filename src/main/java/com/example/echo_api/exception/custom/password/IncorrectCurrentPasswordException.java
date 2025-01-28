package com.example.echo_api.exception.custom.password;

import com.example.echo_api.config.ErrorMessageConfig;
import com.example.echo_api.persistence.dto.request.account.UpdatePasswordDTO;

/**
 * Thrown if an {@link UpdatePasswordDTO} is rejected because the supplied
 * current password does not match the hashed password from db.
 */
public class IncorrectCurrentPasswordException extends PasswordException {

    /**
     * Constructs a {@code IncorrectCurrentPasswordException} with the specified
     * message.
     */
    public IncorrectCurrentPasswordException() {
        super(ErrorMessageConfig.INCORRECT_CURRENT_PASSWORD);
    }

}

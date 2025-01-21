package com.example.echo_api.exception.custom.password;

import com.example.echo_api.config.ErrorMessageConfig;
import com.example.echo_api.persistence.dto.request.account.UpdatePasswordRequest;

/**
 * Thrown if an {@link UpdatePasswordRequest} is rejected because the supplied
 * confirmation password does not match the supplied current password.
 */
public class ConfirmationPasswordMismatchException extends PasswordException {

    /**
     * Constructs a {@code ConfirmationPasswordMismatchException} with the specified
     * message.
     */
    public ConfirmationPasswordMismatchException() {
        super(ErrorMessageConfig.CONFIRMATION_PASSWORD_MISMATCH);
    }

}

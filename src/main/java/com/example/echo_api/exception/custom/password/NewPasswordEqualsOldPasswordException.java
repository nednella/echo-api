package com.example.echo_api.exception.custom.password;

import com.example.echo_api.persistence.dto.request.account.UpdatePasswordRequest;

/**
 * Thrown if an {@link UpdatePasswordRequest} is rejected because the supplied
 * new password is the same as the supplied current password.
 */
public class NewPasswordEqualsOldPasswordException extends PasswordException {

    /**
     * Constructs a {@code NewPasswordEqualsOldPasswordException} with the specified
     * message.
     */
    public NewPasswordEqualsOldPasswordException() {
        super("New password cannot be the same as the current password.");
    }

}

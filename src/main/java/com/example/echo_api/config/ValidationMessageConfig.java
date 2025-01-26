package com.example.echo_api.config;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

/**
 * 
 */
@NoArgsConstructor(access = PRIVATE)
public final class ValidationMessageConfig {

    /* Account */
    public static final String INVALID_USERNAME = "Username must be 3-15 characters long and can only include alphanumerics or underscores.";
    public static final String INVALID_PASSWORD = "Password must be at least 6 characters long and contain at least 1 letter and 1 number.";
    public static final String CONFIRMATION_PASSWORD_MISMATCH = "Confirmation password does not match the new password.";
    public static final String NEW_PASSWORD_NOT_UNIQUE = "New password cannot be the same as the current password.";

}

package com.example.echo_api.config;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

/**
 * 
 */
@NoArgsConstructor(access = PRIVATE)
public final class ErrorMessageConfig {

    /** 400 BAD REQUEST */
    /** Validation */
    public static final String INVALID_REQUEST = "Invalid request.";
    /** Auth */
    public static final String USERNAME_OR_PASSWORD_IS_INCORRECT = "Username or password is incorrect.";
    /** Generic */
    public static final String USERNAME_ARLEADY_EXISTS = "Username already exists.";
    public static final String USERNAME_NOT_FOUND = "Username not found.";
    public static final String CONFIRMATION_PASSWORD_MISMATCH = "Confirmation password does not match the new password.";
    public static final String NEW_PASSWORD_EQUALS_OLD_PASSWORD = "New password cannot be the same as the current password.";
    public static final String INCORRECT_CURRENT_PASSWORD = "Incorrect current password.";

    /** 401 UNAUTHORISED */
    public static final String UNAUTHORISED = "Unauthorised request.";
    public static final String ACCOUNT_STATUS = "Account status is abnormal.";

    /** 403 FORBIDDEN */
    public static final String FORBIDDEN = "Access denied.";

    /** 404 RESOURCE NOT FOUND */
    public static final String NOT_FOUND = "Resource not found.";

    /** 500 INTERNAL SERVER ERROR */
    public static final String INTERNAL_SERVER_ERROR = "An unexpected error occurred.";

}
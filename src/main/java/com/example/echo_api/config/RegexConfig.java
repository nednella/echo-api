package com.example.echo_api.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RegexConfig {

    /**
     * Username regex pattern
     * 
     * <ul>
     * <li>Contains only alphanumerics and underscores
     * <li>Contains between 3 and 15 characters
     * </ul>
     */
    public static final String USERNAME = "^[a-zA-Z0-9_]{3,15}$";
    public static final String USERNAME_MESSAGE = "username must be 3-15 characters long and can only include alphanumerics or underscores";

    /**
     * Password regex pattern
     * 
     * <ul>
     * <li>Contains at least one letter
     * <li>Contains at least one number
     * <li>Contain only non-whitespace characters (excludes space, tabs and line
     * breaks)
     * <li>Contains at least 6 characters
     * </ul>
     */
    public static final String PASSWORD = "^(?=.*[a-zA-Z])(?=.*\\d)[\\S]{6,}$";
    public static final String PASSWORD_MESSAGE = "password must be at least 6 characters long and contain at least 1 letter and 1 number";

}

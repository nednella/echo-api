package com.example.echo_api.config;

public class ValidationConfig {

    public static class Regex {

        /**
         * Username regex pattern
         * 
         * <ul>
         * <li>Contains only alphanumerics and underscores
         * <li>Contains between 3 and 15 characters
         * </ul>
         */
        public static final String username = "^[a-zA-Z0-9_]{3,15}$";
        public static final String usernameMessage = "Username may only contain letters, numbers and underscores";

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
        public static final String password = "^(?=.*[a-zA-Z])(?=.*\\d)[\\S]{6,}$";
        public static final String passwordMessage = "Password must contain at least 1 letter, 1 number and 6 characters";

    }

}

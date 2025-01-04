package com.example.echo_api.config;

/**
 * This class contains the API URL constants used throughout the application..
 * It defines the base URLs for auth and API routes, along with all relevant
 * endpoints.
 */
public final class ApiConfig {

    public static final String AUTH_URL = "/auth";
    public static final String API_URL = "/api/v1";

    public static final class Auth {
        public static final String ROOT = AUTH_URL;
        public static final String LOGIN = "/login";
        public static final String SIGNUP = "/signup";
    }

}

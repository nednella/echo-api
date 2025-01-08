package com.example.echo_api.config;

/**
 * This class contains the API URL constants used throughout the application.
 * It defines the base URLs for auth and API routes, along with all relevant
 * endpoints.
 */
public final class ApiConfig {

    public static final String BASE_URL = "/api/v1";

    public static final class Auth {
        public static final String ROOT = BASE_URL + "/auth";
        public static final String LOGIN = "/login";
        public static final String SIGNUP = "/signup";
    }

    public static final class User {
        public static final String ROOT = BASE_URL + "/user";
        public static final String FIND_ALL = "/list";
        public static final String FIND_BY_USERNAME = "/list/{username}";
    }

}

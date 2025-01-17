package com.example.echo_api.config;

/**
 * This class contains the path constants used throughout the application. It
 * defines the base URL for the API, along with all relevant endpoints.
 */
public final class ApiConfig {
    private ApiConfig() {
    }

    private static final String BASE_URL = "/api/v1";

    public static final class Auth {
        private Auth() {
        }

        private static final String ROOT = BASE_URL + "/auth";
        public static final String LOGIN = ROOT + "/login";
        public static final String SIGNUP = ROOT + "/signup";
    }

    public static final class User {
        private User() {
        }

        private static final String ROOT = BASE_URL + "/user";
        public static final String FIND_ALL = ROOT + "/list";
        public static final String FIND_BY_USERNAME = ROOT + "/list/{username}";
    }

}

package com.example.echo_api.config;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

/**
 * This class contains the path constants used throughout the application. It
 * defines the base URL for the API, along with all relevant endpoints.
 */
@NoArgsConstructor(access = PRIVATE)
public final class ApiConfig {
    private static final String BASE_URL = "/api/v1";

    @NoArgsConstructor(access = PRIVATE)
    public static final class Auth {
        private static final String ROOT = BASE_URL + "/auth";
        public static final String LOGIN = ROOT + "/login";
        public static final String SIGNUP = ROOT + "/signup";
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class Account {
        private static final String ROOT = BASE_URL + "/account";
        public static final String USERNAME_AVAILABLE = ROOT + "/username-available";
        public static final String UPDATE_USERNAME = ROOT + "/username";
        public static final String UPDATE_PASSWORD = ROOT + "/password";
    }

    @NoArgsConstructor(access = PRIVATE)
    public static final class User {
        private static final String ROOT = BASE_URL + "/user";
        public static final String FIND_ALL = ROOT + "/list";
        public static final String FIND_BY_USERNAME = ROOT + "/list/{username}";
    }

}

package com.example.echo_api.exception.custom.username;

public class UsernameAlreadyExistsException extends UsernameException {

    public UsernameAlreadyExistsException() {
        super("Username already exists.");
    }

}
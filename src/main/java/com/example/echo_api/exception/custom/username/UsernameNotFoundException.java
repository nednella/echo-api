package com.example.echo_api.exception.custom.username;

public class UsernameNotFoundException extends UsernameException {

    public UsernameNotFoundException() {
        super("Username not found.");
    }

}

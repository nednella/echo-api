package com.example.echo_api.service.session;

import org.springframework.security.core.AuthenticationException;

import com.example.echo_api.persistence.model.user.User;

public interface SessionService {

    public User getAuthenticatedUser();

    public void authenticate(String username, String password) throws AuthenticationException;

    public void reauthenticate(User user);

}

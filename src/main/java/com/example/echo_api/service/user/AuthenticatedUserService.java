package com.example.echo_api.service.user;

import com.example.echo_api.persistence.model.User;

public interface AuthenticatedUserService {

    public User getAuthenticatedUser();

}

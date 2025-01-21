package com.example.echo_api.service.session;

import com.example.echo_api.persistence.model.User;

public interface SessionService {

    public User getAuthenticatedUser();

}

package com.example.echo_api.service.account;

import com.example.echo_api.persistence.model.User;

public interface AccountService {

    public User getAuthenticatedUser();

}

package com.example.echo_api.service.user;

import java.util.List;

import com.example.echo_api.exception.user.UserNotFoundException;
import com.example.echo_api.model.User;

public interface UserService {

    public List<User> findAll();

    public User findByUsername(String username) throws UserNotFoundException;

}

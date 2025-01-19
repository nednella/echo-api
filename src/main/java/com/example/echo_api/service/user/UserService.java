package com.example.echo_api.service.user;

import java.util.List;

import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.persistence.model.User;

public interface UserService {

    public List<User> findAll();

    public User findByUsername(String username) throws UsernameException;

    public boolean existsByUsername(String username);

    default void createUser(String username, String password) throws UsernameException {
        createUser(username, password, "ROLE_USER");
    }

    public void createUser(String username, String password, String role) throws UsernameException;

}

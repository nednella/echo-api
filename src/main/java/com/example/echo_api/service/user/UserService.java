package com.example.echo_api.service.user;

import java.util.List;
import java.util.Optional;

import com.example.echo_api.exception.user.UserAlreadyExistsException;
import com.example.echo_api.exception.user.UserNotFoundException;
import com.example.echo_api.persistence.model.User;

public interface UserService {

    public List<User> findAll();

    public Optional<User> findByUsername(String username);

    public boolean existsByUsername(String username);

    default void createUser(String username, String password) throws UserAlreadyExistsException {
        createUser(username, password, "ROLE_USER");
    };

    public void createUser(String username, String password, String role) throws UserAlreadyExistsException;

    public void enableUser(String username) throws UserNotFoundException;

    public void disableUser(String username) throws UserNotFoundException;

    public void updateUsername(String username, String newUsername) throws UserNotFoundException;

    public void updatePassword(String username, String newPassword) throws UserNotFoundException;

}

package com.example.echo_api.service.account;

import java.util.List;

import com.example.echo_api.exception.custom.password.PasswordException;
import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.persistence.dto.request.account.UpdatePasswordRequest;
import com.example.echo_api.persistence.model.User;

public interface AccountService {

    public void register(String username, String password) throws UsernameException;

    public void registerWithRole(String username, String password, String role) throws UsernameException;

    public List<User> findAll();

    public User findByUsername(String username) throws UsernameException;

    public boolean existsByUsername(String username);

    public void updateUsername(String username) throws UsernameException;

    public void updatePassword(UpdatePasswordRequest request) throws PasswordException;

}

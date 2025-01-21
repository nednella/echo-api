package com.example.echo_api.controller.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.persistence.model.User;
import com.example.echo_api.service.account.AccountService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final AccountService accountService;

    @GetMapping(ApiConfig.User.FIND_ALL)
    public ResponseEntity<List<User>> findAll() {
        List<User> users = accountService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping(ApiConfig.User.FIND_BY_USERNAME)
    public ResponseEntity<User> findByUsername(@PathVariable("username") String username) {
        User user = accountService.findByUsername(username);
        return ResponseEntity.ok(user);
    }

}

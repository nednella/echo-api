package com.example.echo_api.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.persistence.dto.request.auth.LoginRequest;
import com.example.echo_api.persistence.dto.request.auth.SignupRequest;
import com.example.echo_api.service.auth.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(ApiConfig.Auth.LOGIN)
    public ResponseEntity<Void> signIn(@RequestBody @Valid LoginRequest login) {
        authService.signIn(login.username(), login.password());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(ApiConfig.Auth.SIGNUP)
    public ResponseEntity<Void> signUp(@RequestBody @Valid SignupRequest signup) {
        authService.signUp(signup.username(), signup.password());
        return ResponseEntity.noContent().build();
    }

}

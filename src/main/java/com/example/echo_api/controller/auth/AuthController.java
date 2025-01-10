package com.example.echo_api.controller.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.controller.auth.request.SignInRequest;
import com.example.echo_api.controller.auth.request.SignUpRequest;
import com.example.echo_api.service.auth.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiConfig.Auth.ROOT)
public class AuthController {

    private final AuthService authService;

    @PostMapping(ApiConfig.Auth.LOGIN)
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest login) {
        authService.signIn(login.getUsername(), login.getPassword());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(ApiConfig.Auth.SIGNUP)
    public ResponseEntity<?> signUp(@RequestBody @Valid SignUpRequest signup) {
        authService.signUp(signup.getUsername(), signup.getPassword());
        return ResponseEntity.noContent().build();
    }

}

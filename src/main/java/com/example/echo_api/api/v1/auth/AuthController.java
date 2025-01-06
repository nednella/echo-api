package com.example.echo_api.api.v1.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.echo_api.api.v1.auth.request.SignInRequest;
import com.example.echo_api.api.v1.auth.request.SignUpReqest;
import com.example.echo_api.config.ApiConfig;
import com.example.echo_api.service.auth.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(ApiConfig.Auth.ROOT)
public class AuthController {

    private final AuthService authService;

    @PostMapping(ApiConfig.Auth.LOGIN)
    public ResponseEntity<String> signIn(@RequestBody @Valid SignInRequest signInRequest) {
        authService.signIn(signInRequest);
        return ResponseEntity.ok("Authentication successful!");
    }

    @PostMapping(ApiConfig.Auth.SIGNUP)
    public ResponseEntity<String> signUp(@RequestBody @Valid SignUpReqest signUpRequest) {
        authService.signUp(signUpRequest);
        return ResponseEntity.ok("Registration successful!");
    }

}

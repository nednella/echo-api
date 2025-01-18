package com.example.echo_api.persistence.dto.request.auth;

import com.example.echo_api.validation.Password;
import com.example.echo_api.validation.Username;

import jakarta.validation.constraints.NotBlank;

// @formatter:off
public record SignUpRequest(

    @Username
    @NotBlank(message = "username is required")
    String username,
    
    @Password
    @NotBlank(message = "password is required")
    String password

) {}
// @formatter:on

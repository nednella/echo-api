package com.example.echo_api.persistence.dto.request.auth;

import jakarta.validation.constraints.NotBlank;

// @formatter:off
public record SignInRequest(

    @NotBlank(message = "Username is required")
    String username,
    
    @NotBlank(message = "Password is required")
    String password

) {}
// @formatter:on

package com.example.echo_api.persistence.dto.request.auth;

import jakarta.validation.constraints.NotNull;

// @formatter:off
public record LoginRequest(

    @NotNull(message = "Username is required.")
    String username,
    
    @NotNull(message = "Password is required.")
    String password

) {}
// @formatter:on

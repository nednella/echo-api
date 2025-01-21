package com.example.echo_api.persistence.dto.request.account;

import com.example.echo_api.validation.Username;

import jakarta.validation.constraints.NotEmpty;

// @formatter:off
public record UpdateUsernameRequest(

    @Username
    @NotEmpty(message = "Username is required.")
    String username

) {}
// @formatter:on
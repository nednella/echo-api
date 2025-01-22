package com.example.echo_api.persistence.dto.request.account;

import com.example.echo_api.validation.Username;

import jakarta.validation.constraints.NotBlank;

// @formatter:off
public record UpdateUsernameRequest(

    @Username
    @NotBlank(message = "Username is required.")
    String username

) {}
// @formatter:on
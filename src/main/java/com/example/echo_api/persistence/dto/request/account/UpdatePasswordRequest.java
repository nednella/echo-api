package com.example.echo_api.persistence.dto.request.account;

import com.example.echo_api.validation.Password;

import jakarta.validation.constraints.NotEmpty;

// @formatter:off
public record UpdatePasswordRequest(

    @NotEmpty(message = "Current password is required.")
    String current_password,

    @Password
    @NotEmpty(message = "New password is required.")
    String new_password,

    @NotEmpty(message = "Confirmation password is required.")
    String confirmation_password

) {}
// @formatter:on

package com.example.echo_api.persistence.dto.request.account;

import com.example.echo_api.validation.Password;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotEmpty;

// @formatter:off
public record UpdatePasswordRequest(

    @NotEmpty(message = "Current password is required.")
    @JsonProperty("current_password")
    String currentPassword,

    @Password
    @NotEmpty(message = "New password is required.")
    @JsonProperty("new_password")
    String newPassword,

    @NotEmpty(message = "Confirmation password is required.")
    @JsonProperty("confirmation_password")
    String confirmationPassword

) {}
// @formatter:on

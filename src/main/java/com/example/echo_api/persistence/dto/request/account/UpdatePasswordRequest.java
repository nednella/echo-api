package com.example.echo_api.persistence.dto.request.account;

import com.example.echo_api.validation.Password;
import com.example.echo_api.validation.ConfirmationPasswordMatch;
import com.example.echo_api.validation.NewPasswordUnique;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;

// @formatter:off
@NewPasswordUnique
@ConfirmationPasswordMatch
public record UpdatePasswordRequest(

    @NotBlank(message = "Current password is required.")
    @JsonProperty("current_password")
    String currentPassword,

    @Password
    @NotBlank(message = "New password is required.")
    @JsonProperty("new_password")
    String newPassword,

    @NotBlank(message = "Confirmation password is required.")
    @JsonProperty("confirmation_password")
    String confirmationPassword

) {}
// @formatter:on

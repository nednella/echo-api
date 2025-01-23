package com.example.echo_api.persistence.dto.request.account;

import com.example.echo_api.validation.Password;
import com.example.echo_api.validation.sequence.AdvancedCheck;
import com.example.echo_api.validation.sequence.BasicCheck;
import com.example.echo_api.validation.ConfirmationPasswordMatch;
import com.example.echo_api.validation.NewPasswordUnique;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotNull;

// @formatter:off
@GroupSequence({  })
@NewPasswordUnique(groups = AdvancedCheck.class)
@ConfirmationPasswordMatch(groups = AdvancedCheck.class)
public record UpdatePasswordRequest(

    @NotNull(message = "Current password is required.", groups = BasicCheck.class)
    @JsonProperty("current_password")
    String currentPassword,

    @NotNull(message = "New password is required.", groups = BasicCheck.class)
    @Password(groups = AdvancedCheck.class)
    @JsonProperty("new_password")
    String newPassword,

    @NotNull(message = "Confirmation password is required.", groups = BasicCheck.class)
    @JsonProperty("confirmation_password")
    String confirmationPassword

) {}
// @formatter:on

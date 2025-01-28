package com.example.echo_api.persistence.dto.request.account;

import com.example.echo_api.validation.account.annotations.ConfirmationPasswordMatch;
import com.example.echo_api.validation.account.annotations.NewPasswordUnique;
import com.example.echo_api.validation.account.annotations.Password;
import com.example.echo_api.validation.sequence.AdvancedCheck;
import com.example.echo_api.validation.sequence.BasicCheck;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

/**
 * Represents a request to update the password of the authenticated account.
 * 
 * @param currentPassword      The current password of the account. Required
 *                             field. Must not match {@code newPassword}.
 * @param newPassword          The new password for the account. Required field.
 *                             Must not match {@code currentPassword}.
 * @param confirmationPassword The confirmation password for the account.
 *                             Required field. Must match {@code newPassword}.
 */
// @formatter:off
@NewPasswordUnique(groups = AdvancedCheck.class)
@ConfirmationPasswordMatch(groups = AdvancedCheck.class)
public record UpdatePasswordDTO(

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

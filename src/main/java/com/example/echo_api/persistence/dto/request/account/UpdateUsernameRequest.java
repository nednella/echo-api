package com.example.echo_api.persistence.dto.request.account;

import com.example.echo_api.validation.account.annotations.Username;
import com.example.echo_api.validation.sequence.AdvancedCheck;
import com.example.echo_api.validation.sequence.BasicCheck;

import jakarta.validation.constraints.NotNull;

/**
 * Represents a request to update the username of the authenticated user.
 * 
 * @param username The new username for the user. Required field. Must match the
 *                 format specified by {@link Username}.
 */
// @formatter:off
public record UpdateUsernameRequest(

    @NotNull(message = "Username is required.", groups = BasicCheck.class)
    @Username(groups = AdvancedCheck.class)
    String username

) {}
// @formatter:on
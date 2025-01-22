package com.example.echo_api.persistence.dto.request.account;

import com.example.echo_api.validation.Username;
import com.example.echo_api.validation.sequence.AdvancedCheck;
import com.example.echo_api.validation.sequence.BasicCheck;

import jakarta.validation.constraints.NotBlank;

// @formatter:off
public record UpdateUsernameRequest(

    @NotBlank(message = "Username is required.", groups = BasicCheck.class)
    @Username(groups = AdvancedCheck.class)
    String username

) {}
// @formatter:on
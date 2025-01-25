package com.example.echo_api.persistence.dto.request.auth;

import com.example.echo_api.validation.account.annotations.Password;
import com.example.echo_api.validation.account.annotations.Username;
import com.example.echo_api.validation.sequence.AdvancedCheck;
import com.example.echo_api.validation.sequence.BasicCheck;

import jakarta.validation.constraints.NotNull;

// @formatter:off
public record SignupRequest(

    @NotNull(message = "Username is required.", groups = BasicCheck.class)
    @Username(groups = AdvancedCheck.class)
    String username,
    
    @NotNull(message = "Password is required.", groups = BasicCheck.class)
    @Password(groups = AdvancedCheck.class)
    String password
    
) {}
// @formatter:on

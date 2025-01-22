package com.example.echo_api.persistence.dto.request.auth;

import com.example.echo_api.validation.Password;
import com.example.echo_api.validation.Username;
import com.example.echo_api.validation.sequence.AdvancedCheck;
import com.example.echo_api.validation.sequence.BasicCheck;

import jakarta.validation.constraints.NotBlank;

// @formatter:off
public record SignupRequest(

    @NotBlank(message = "username is required", groups = BasicCheck.class)
    @Username(groups = AdvancedCheck.class)
    String username,
    
    @NotBlank(message = "password is required", groups = BasicCheck.class)
    @Password(groups = AdvancedCheck.class)
    String password
    
) {}
// @formatter:on

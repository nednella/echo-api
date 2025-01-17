package com.example.echo_api.persistence.dto.request.auth;

import com.example.echo_api.config.ValidationConfig;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// @formatter:off
public record SignUpRequest(
    @NotBlank(message = "username is required") 
    @Pattern(regexp = ValidationConfig.Regex.USERNAME, message = ValidationConfig.Regex.USERNAME_MESSAGE) 
    String username,

    @NotBlank(message = "password is required") 
    @Pattern(regexp = ValidationConfig.Regex.PASSWORD, message = ValidationConfig.Regex.PASSWORD_MESSAGE) 
    String password
) {}
// @formatter:on

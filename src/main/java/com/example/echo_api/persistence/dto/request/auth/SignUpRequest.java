package com.example.echo_api.persistence.dto.request.auth;

import com.example.echo_api.config.ValidationConfig;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

// @formatter:off
public record SignUpRequest(
    @NotBlank(message = "username is required") 
    @Pattern(regexp = ValidationConfig.Regex.username, message = ValidationConfig.Regex.usernameMessage) 
    String username,

    @NotBlank(message = "password is required") 
    @Pattern(regexp = ValidationConfig.Regex.password, message = ValidationConfig.Regex.passwordMessage) 
    String password
) {}
// @formatter:on

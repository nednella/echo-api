package com.example.echo_api.persistence.dto.request.auth;

import com.example.echo_api.config.ValidationConfig;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "username is required")
    @Pattern(regexp = ValidationConfig.Regex.username, message = ValidationConfig.Regex.usernameMessage)
    private final String username;

    @NotBlank(message = "password is required")
    @Pattern(regexp = ValidationConfig.Regex.password, message = ValidationConfig.Regex.passwordMessage)
    private final String password;

}

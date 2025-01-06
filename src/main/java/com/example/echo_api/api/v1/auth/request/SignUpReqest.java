package com.example.echo_api.api.v1.auth.request;

import com.example.echo_api.config.ValidationConfig;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignUpReqest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 15, message = "Username must be between 3 and 15 characters")
    @Pattern(regexp = ValidationConfig.Regex.username, message = ValidationConfig.Regex.usernameMessage)
    private final String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    @Pattern(regexp = ValidationConfig.Regex.password, message = ValidationConfig.Regex.passwordMessage)
    private final String password;

}

package com.example.echo_api.persistence.dto.request.profile;

import jakarta.validation.constraints.Size;

// @formatter:off
public record UpdateProfileInfoRequest(

    @Size(max = 50, message = "Name must not exceed 50 characters.")
    String name,

    @Size(max = 160, message = "Bio must not exceed 160 characters.")
    String bio,

    @Size(max = 30, message = "Location must not exceed 30 characters.")
    String location
    
) {}
// @formatter:on

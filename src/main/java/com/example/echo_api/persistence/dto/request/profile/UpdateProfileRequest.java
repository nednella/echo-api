package com.example.echo_api.persistence.dto.request.profile;

// @formatter:off
public record UpdateProfileRequest(

    String name,
    String bio,
    String location
    
) {}
// @formatter:on

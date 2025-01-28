package com.example.echo_api.persistence.dto.request.profile;

import jakarta.validation.constraints.Size;

/**
 * Represents a request to update the authenticated user's profile information.
 * 
 * @param name     The name for the user's profile. Must not exceed 50
 *                 characters.
 * @param bio      The bio for the user's profile. Must not exceed 160
 *                 characters.
 * @param location The location for the user's profile. Must not exceed 30
 *                 characters.
 */
// @formatter:off
public record UpdateProfileDTO(

    @Size(max = 50, message = "Name must not exceed 50 characters.")
    String name,

    @Size(max = 160, message = "Bio must not exceed 160 characters.")
    String bio,

    @Size(max = 30, message = "Location must not exceed 30 characters.")
    String location
    
) {}
// @formatter:on

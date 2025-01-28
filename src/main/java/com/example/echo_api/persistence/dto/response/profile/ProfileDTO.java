package com.example.echo_api.persistence.dto.response.profile;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a standardised response format for an {@link Account} profile.
 *
 * @param username       The username of the account associated to the profile.
 * @param name           The profile name.
 * @param bio            The profile bio.
 * @param location       The profile location.
 * @param avatarUrl      The URL of the profile avatar image.
 * @param bannerUrl      The URL of the profile banner image.
 * @param followingCount The number of profiles this profile is following.
 * @param followerCount  The number of followers this profile has.
 * @param postCount      The number of posts this profile has made.
 * @param mediaCount     The number of media items this profile has uploaded.
 * @param createdAt      The timestamp when the profile was created (ISO-8601
 *                       format).
 */
// @formatter:off
public record ProfileDTO(
    String username,
    String name,
    String bio,
    String location,
    @JsonProperty("avatar_url") String avatarUrl,
    @JsonProperty("banner_url") String bannerUrl,
    @JsonProperty("created_at") String createdAt
) {

    public ProfileDTO(
        String username,
        String name,
        String bio,
        String location,
        String avatarUrl,
        String bannerUrl,
        LocalDateTime createdAt
    ) {
        this(
            username,
            name,
            bio,
            location,
            avatarUrl,
            bannerUrl,
            createdAt != null ? createdAt.toString() : null
        );
    }

}
// @formatter:on

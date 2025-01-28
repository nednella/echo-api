package com.example.echo_api.persistence.dto.response.profile;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a standardised response format for an {@link Account} profile.
 *
 * @param username  The username of the account associated to the profile.
 * @param name      The profile name.
 * @param bio       The profile bio.
 * @param location  The profile location.
 * @param avatarUrl The URL of the profile avatar image.
 * @param bannerUrl The URL of the profile banner image.
 * @param createdAt The timestamp when the profile was created (ISO-8601
 *                  format).
 * @param metrics   The profile metrics.
 */
// @formatter:off
public record ProfileDTO(
    String username,
    String name,
    String bio,
    String location,
    @JsonProperty("avatar_url") String avatarUrl,
    @JsonProperty("banner_url") String bannerUrl,
    @JsonProperty("created_at") String createdAt,
    @JsonProperty("profile_metrics") MetricsDTO metrics
) {

    public ProfileDTO(
        String username,
        String name,
        String bio,
        String location,
        String avatarUrl,
        String bannerUrl,
        LocalDateTime createdAt,
        MetricsDTO metrics
    ) {
        this(
            username,
            name,
            bio,
            location,
            avatarUrl,
            bannerUrl,
            createdAt != null ? createdAt.toString() : null,
            metrics
        );
    }

}
// @formatter:on

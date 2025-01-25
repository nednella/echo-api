package com.example.echo_api.persistence.dto.response.profile;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

// @formatter:off
public record ProfileResponse(
    String username,
    @JsonProperty("display_name") String displayName,
    String bio,
    String location,
    @JsonProperty("avatar_url") String avatarUrl,
    @JsonProperty("banner_url") String bannerUrl,
    @JsonProperty("following_count") int followingCount,
    @JsonProperty("follower_count") int followerCount,
    @JsonProperty("created_at") String createdAt
) {

    public ProfileResponse(
        String username,
        String displayName,
        String bio,
        String location,
        String avatarUrl,
        String bannerUrl,
        int followingCount,
        int followerCount,
        LocalDateTime createdAt
    ) {
        this(
            username,
            displayName,
            bio,
            location,
            avatarUrl,
            bannerUrl,
            followingCount,
            followerCount,
            createdAt.toString()
        );
    }

}
// @formatter:on

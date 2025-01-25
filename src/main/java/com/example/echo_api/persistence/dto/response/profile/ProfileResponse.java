package com.example.echo_api.persistence.dto.response.profile;

import java.time.LocalDateTime;

// @formatter:off
public record ProfileResponse(
    String username,
    String displayName,
    String bio,
    String location,
    String avatarUrl,
    String bannerUrl,
    int followingCount,
    int followerCount,
    String createdAt
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

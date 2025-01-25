package com.example.echo_api.persistence.dto.response.profile;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

// @formatter:off
public record ProfileResponse(
    String username,
    String name,
    String bio,
    String location,
    @JsonProperty("avatar_url") String avatarUrl,
    @JsonProperty("banner_url") String bannerUrl,
    @JsonProperty("following_count") int followingCount,
    @JsonProperty("follower_count") int followerCount,
    @JsonProperty("post_count") int postCount,
    @JsonProperty("media_count") int mediaCount,
    @JsonProperty("created_at") String createdAt
) {

    public ProfileResponse(
        String username,
        String name,
        String bio,
        String location,
        String avatarUrl,
        String bannerUrl,
        int followingCount,
        int followerCount,
        int postCount,
        int mediaCount,
        LocalDateTime createdAt
    ) {
        this(
            username,
            name,
            bio,
            location,
            avatarUrl,
            bannerUrl,
            followingCount,
            followerCount,
            postCount,
            mediaCount,
            createdAt.toString()
        );
    }

}
// @formatter:on

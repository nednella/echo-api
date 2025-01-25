package com.example.echo_api.persistence.dto.response.profile;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a standardised response format for a user profile.
 *
 * @param username       The username of the user.
 * @param name           The name of the user.
 * @param bio            The bio or description of the user.
 * @param location       The location of the user.
 * @param avatarUrl      The URL of the user's avatar image.
 * @param bannerUrl      The URL of the user's banner image.
 * @param followingCount The number of users this user is following.
 * @param followerCount  The number of followers this user has.
 * @param postCount      The number of posts this user has made.
 * @param mediaCount     The number of media items this user has uploaded.
 * @param createdAt      The timestamp when the user's profile was created
 *                       (ISO-8601 format).
 */
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

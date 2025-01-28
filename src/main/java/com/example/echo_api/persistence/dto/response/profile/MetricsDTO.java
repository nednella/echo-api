package com.example.echo_api.persistence.dto.response.profile;

import com.fasterxml.jackson.annotation.JsonProperty;

// @formatter:off
public record MetricsDTO(
    @JsonProperty("following_count") int followingCount,
    @JsonProperty("follower_count") int followerCount,
    @JsonProperty("post_count") int postCount,
    @JsonProperty("media_count") int mediaCount
) {}
// @formatter:on

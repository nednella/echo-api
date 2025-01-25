package com.example.echo_api.persistence.mapper;

import static lombok.AccessLevel.PRIVATE;

import com.example.echo_api.persistence.dto.response.profile.ProfileResponse;
import com.example.echo_api.persistence.model.profile.Profile;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ProfileMapper {

    public static ProfileResponse toResponse(Profile profile) {
        return new ProfileResponse(
            profile.getUsername(),
            profile.getName(),
            profile.getBio(),
            profile.getLocation(),
            profile.getAvatarUrl(),
            profile.getBannerUrl(),
            profile.getFollowingCount(),
            profile.getFollowingCount(),
            profile.getPostCount(),
            profile.getMediaCount(),
            profile.getCreatedAt());
    }

}

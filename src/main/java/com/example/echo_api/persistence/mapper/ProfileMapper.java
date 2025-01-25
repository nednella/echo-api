package com.example.echo_api.persistence.mapper;

import static lombok.AccessLevel.PRIVATE;

import com.example.echo_api.persistence.dto.request.profile.UpdateProfileRequest;
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

    public static Profile updateProfile(UpdateProfileRequest request, Profile profile) {
        if (request.name() != null) {
            profile.setName(request.name());
        }
        if (request.bio() != null) {
            profile.setBio(request.bio());
        }
        if (request.location() != null) {
            profile.setLocation(request.location());
        }

        return profile;
    }

}

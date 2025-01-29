package com.example.echo_api.unit.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.example.echo_api.persistence.dto.request.profile.UpdateProfileDTO;
import com.example.echo_api.persistence.dto.response.profile.ProfileDTO;
import com.example.echo_api.persistence.dto.response.profile.SocialContextDTO;
import com.example.echo_api.persistence.mapper.ProfileMapper;
import com.example.echo_api.persistence.model.account.Account;
import com.example.echo_api.persistence.model.profile.Metrics;
import com.example.echo_api.persistence.model.profile.Profile;

class ProfileMapperTest {

    @Test
    void ProfileMapper_toDTO() {
        Account account = new Account("test", "test");
        Profile profile = new Profile(account);
        Metrics metrics = new Metrics(profile);
        SocialContextDTO context = new SocialContextDTO(false, false, false, false);
        ProfileDTO response = ProfileMapper.toDTO(profile, metrics, context);

        assertNotNull(response);
        assertEquals(account.getUsername(), response.username());
        assertEquals(profile.getName(), response.name());
        assertEquals(profile.getBio(), response.bio());
        assertEquals(profile.getLocation(), response.location());
        assertEquals(profile.getAvatarUrl(), response.avatarUrl());
        assertEquals(profile.getBannerUrl(), response.bannerUrl());
        assertEquals(metrics.getFollowingCount(), response.metrics().followingCount());
        assertEquals(metrics.getFollowerCount(), response.metrics().followerCount());
        assertEquals(metrics.getPostCount(), response.metrics().postCount());
        assertEquals(metrics.getMediaCount(), response.metrics().mediaCount());
        assertEquals(context.following(), response.context().following());
        assertEquals(context.followedBy(), response.context().followedBy());
        assertEquals(context.blocking(), response.context().blocking());
        assertEquals(context.blockedBy(), response.context().blockedBy());
    }

    @Test
    void ProfileMapper_updateProfile() {
        Account account = new Account("test", "test");
        Profile profile = new Profile(account);
        UpdateProfileDTO request = new UpdateProfileDTO("name", "bio", "location");

        Profile updated = ProfileMapper.updateProfile(request, profile);

        assertNotNull(updated);
        assertEquals(request.name(), updated.getName());
        assertEquals(request.location(), updated.getLocation());
        assertEquals(request.bio(), updated.getBio());
    }

}

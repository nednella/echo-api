package com.example.echo_api.service.socialcontext;

import org.springframework.stereotype.Service;

import com.example.echo_api.persistence.model.follow.Follow;
import com.example.echo_api.persistence.model.profile.Profile;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing CRUD operations of {@link Profile}
 * relationships.
 * 
 * @see Follow
 */
@Service
@RequiredArgsConstructor
public class SocialContextServiceImpl implements SocialContextService {

    @Override
    public void follow(Profile source, Profile target) {
        throw new UnsupportedOperationException("Unimplemented method 'follow'");
    }

    @Override
    public void unfollow(Profile source, Profile target) {
        throw new UnsupportedOperationException("Unimplemented method 'unfollow'");
    }

    @Override
    public boolean isFollowing(Profile source, Profile target) {
        throw new UnsupportedOperationException("Unimplemented method 'isFollowing'");
    }

    @Override
    public boolean isFollowedBy(Profile source, Profile target) {
        throw new UnsupportedOperationException("Unimplemented method 'isFollowedBy'");
    }

}

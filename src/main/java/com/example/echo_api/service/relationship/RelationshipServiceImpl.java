package com.example.echo_api.service.relationship;

import org.springframework.stereotype.Service;
import com.example.echo_api.persistence.dto.response.profile.RelationshipDTO;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.service.follow.FollowService;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing CRUD operations of {@link Profile}
 * relationships.
 * 
 * @see FollowService
 */
@Service
@RequiredArgsConstructor
public class RelationshipServiceImpl implements RelationshipService {

    private final FollowService followService;

    @Override
    public RelationshipDTO getRelationship(Profile source, Profile target) {
        boolean isFollowing = isFollowing(source, target);
        boolean isFollowedBy = isFollowedBy(source, target);
        return new RelationshipDTO(isFollowing, isFollowedBy, false, false);
    }

    @Override
    public void follow(Profile source, Profile target) {
        followService.follow(source.getProfileId(), target.getProfileId());
    }

    @Override
    public void unfollow(Profile source, Profile target) {
        followService.unfollow(source.getProfileId(), target.getProfileId());
    }

    /**
     * Internal method for checking if the {@code source} is following the
     * {@code target}.
     * 
     * @param source The source {@link Profile}.
     * @param target The target {@link Profile}.
     * @return Boolean representing existence of the relationship.
     */
    private boolean isFollowing(Profile source, Profile target) {
        return followService.isFollowing(source.getProfileId(), target.getProfileId());
    }

    /**
     * Internal method for checking if the {@code source} is followed by the
     * {@code target}.
     * 
     * @param source The source {@link Profile}.
     * @param target The target {@link Profile}.
     * @return Boolean representing existence of the relationship.
     */
    private boolean isFollowedBy(Profile source, Profile target) {
        return followService.isFollowedBy(source.getProfileId(), target.getProfileId());
    }

}

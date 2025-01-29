package com.example.echo_api.service.relationship;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.echo_api.exception.custom.relationship.BlockedException;
import com.example.echo_api.persistence.dto.response.profile.RelationshipDTO;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.service.block.BlockService;
import com.example.echo_api.service.follow.FollowService;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing relationships between {@link Profile}
 * pairs.
 * 
 * @see FollowService
 * @see BlockService
 */
@Service
@RequiredArgsConstructor
public class RelationshipServiceImpl implements RelationshipService {

    private final FollowService followService;
    private final BlockService blockService;

    @Override
    public RelationshipDTO getRelationship(Profile source, Profile target) {
        boolean isFollowing = isFollowing(source, target);
        boolean isFollowedBy = isFollowedBy(source, target);
        boolean isBlocking = isBlocking(source, target);
        boolean isBlockedBy = isBlockedBy(source, target);
        return new RelationshipDTO(isFollowing, isFollowedBy, isBlocking, isBlockedBy);
    }

    @Override
    public void follow(Profile source, Profile target) {
        if (isBlockedBy(source, target)) {
            throw new BlockedException();
        }
        followService.follow(source.getProfileId(), target.getProfileId());
    }

    @Override
    public void unfollow(Profile source, Profile target) {
        followService.unfollow(source.getProfileId(), target.getProfileId());
    }

    @Override
    @Transactional
    public void block(Profile source, Profile target) {
        handleBlock(source, target);
        blockService.block(source.getProfileId(), target.getProfileId());
    }

    @Override
    public void unblock(Profile source, Profile target) {
        blockService.unblock(source.getProfileId(), target.getProfileId());
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

    /**
     * Internal method for checking if the {@code source} is blocking the
     * {@code target}.
     * 
     * @param source The source {@link Profile}.
     * @param target The target {@link Profile}.
     * @return Boolean representing existence of the relationship.
     */
    private boolean isBlocking(Profile source, Profile target) {
        return blockService.isBlocking(source.getProfileId(), target.getProfileId());
    }

    /**
     * Internal method for checking if the {@code source} is blocked by the
     * {@code target}.
     * 
     * @param source The source {@link Profile}.
     * @param target The target {@link Profile}.
     * @return Boolean representing existence of the relationship.
     */
    private boolean isBlockedBy(Profile source, Profile target) {
        return blockService.isBlockedBy(source.getProfileId(), target.getProfileId());
    }

    /**
     * Internal method for removing any existing relationships between profiles when
     * the {code source} request to block the {@code target}.
     * 
     * @param source The source {@link Profile}.
     * @param target The target {@link Profile}.
     */
    private void handleBlock(Profile source, Profile target) {
        if (isFollowing(source, target)) {
            followService.unfollow(source.getProfileId(), target.getProfileId());
        }
        if (isFollowedBy(source, target)) {
            followService.unfollow(target.getProfileId(), source.getProfileId());
        }
    }

}

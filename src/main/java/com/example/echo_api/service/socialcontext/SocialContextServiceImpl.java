package com.example.echo_api.service.socialcontext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.echo_api.exception.custom.socialcontext.AlreadyFollowingException;
import com.example.echo_api.exception.custom.socialcontext.NotFollowingException;
import com.example.echo_api.persistence.model.follow.Follow;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.repository.FollowRepository;
import com.example.echo_api.service.metrics.MetricsService;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing CRUD operations of {@link Profile}
 * relationships.
 * 
 * @see Follow
 * @see FollowRepository
 */
@Service
@RequiredArgsConstructor
public class SocialContextServiceImpl implements SocialContextService {

    private final MetricsService metricsService;

    private final FollowRepository followRepository;

    @Override
    @Transactional
    public void follow(Profile source, Profile target) {
        if (isFollowing(source, target)) {
            throw new AlreadyFollowingException();
        }

        metricsService.incrementFollowing(source.getProfileId());
        metricsService.incrementFollowers(target.getProfileId());
        followRepository.save(new Follow(source.getProfileId(), target.getProfileId()));
    }

    @Override
    @Transactional
    public void unfollow(Profile source, Profile target) {
        if (!isFollowing(source, target)) {
            throw new NotFollowingException();
        }

        metricsService.decrementFollowing(source.getProfileId());
        metricsService.decrementFollowers(target.getProfileId());
        followRepository.delete(new Follow(source.getProfileId(), target.getProfileId()));
    }

    @Override
    public boolean isFollowing(Profile source, Profile target) {
        return followRepository.existsByFollowerIdAndFollowingId(source.getProfileId(), target.getProfileId());
    }

    @Override
    public boolean isFollowedBy(Profile source, Profile target) {
        return isFollowing(target, source);
    }

}

package com.example.echo_api.service.follow;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.echo_api.exception.custom.socialcontext.AlreadyFollowingException;
import com.example.echo_api.exception.custom.socialcontext.NotFollowingException;
import com.example.echo_api.persistence.model.follow.Follow;
import com.example.echo_api.persistence.repository.FollowRepository;
import com.example.echo_api.service.metrics.MetricsService;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing CRD operations of {@link Follow}
 * relationships.
 * 
 * @see Follow
 * @see FollowRepository
 */
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final MetricsService metricsService;

    private final FollowRepository followRepository;

    @Override
    @Transactional
    public void follow(UUID source, UUID target) {
        if (isFollowing(source, target)) {
            throw new AlreadyFollowingException();
        }

        metricsService.incrementFollowing(source);
        metricsService.incrementFollowers(target);
        followRepository.save(new Follow(source, target));
    }

    @Override
    @Transactional
    public void unfollow(UUID source, UUID target) {
        if (!isFollowing(source, target)) {
            throw new NotFollowingException();
        }

        metricsService.decrementFollowing(source);
        metricsService.decrementFollowers(target);
        followRepository.delete(new Follow(source, target));
    }

    @Override
    public boolean isFollowing(UUID source, UUID target) {
        return followRepository.existsByFollowerIdAndFollowingId(source, target);
    }

    @Override
    public boolean isFollowedBy(UUID source, UUID target) {
        return isFollowing(target, source);
    }

}

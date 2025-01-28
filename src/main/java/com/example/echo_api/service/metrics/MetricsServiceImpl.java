package com.example.echo_api.service.metrics;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.echo_api.exception.custom.account.IdNotFoundException;
import com.example.echo_api.persistence.model.profile.Metrics;
import com.example.echo_api.persistence.repository.MetricsRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing CRUD operations of profile
 * {@link Metrics}.
 * 
 * @see MetricsRepository
 */
@Service
@RequiredArgsConstructor
public class MetricsServiceImpl implements MetricsService {

    private MetricsRepository metricsRepository;

    @Override
    public Metrics getMetrics(UUID profileId) throws IdNotFoundException {
        return findById(profileId);
    }

    @Override
    public void incrementFollowing(UUID profileId) throws IdNotFoundException {
        Metrics metrics = findById(profileId);
        metrics.incrementFollowingCount();
        metricsRepository.save(metrics);
    }

    @Override
    public void incrementFollowers(UUID profileId) throws IdNotFoundException {
        Metrics metrics = findById(profileId);
        metrics.incrementFollowerCount();
        metricsRepository.save(metrics);
    }

    @Override
    public void incrementPosts(UUID profileId) throws IdNotFoundException {
        Metrics metrics = findById(profileId);
        metrics.incrementPostCount();
        metricsRepository.save(metrics);
    }

    @Override
    public void incrementMedia(UUID profileId) throws IdNotFoundException {
        Metrics metrics = findById(profileId);
        metrics.incrementMediaCount();
        metricsRepository.save(metrics);
    }

    @Override
    public void decrementFollowing(UUID profileId) throws IdNotFoundException {
        Metrics metrics = findById(profileId);
        metrics.decrementFollowingCount();
        metricsRepository.save(metrics);
    }

    @Override
    public void decrementFollowers(UUID profileId) throws IdNotFoundException {
        Metrics metrics = findById(profileId);
        metrics.decrementFollowerCount();
        metricsRepository.save(metrics);
    }

    @Override
    public void decrementPosts(UUID profileId) throws IdNotFoundException {
        Metrics metrics = findById(profileId);
        metrics.decrementPostCount();
        metricsRepository.save(metrics);
    }

    @Override
    public void decrementMedia(UUID profileId) throws IdNotFoundException {
        Metrics metrics = findById(profileId);
        metrics.decrementMediaCount();
        metricsRepository.save(metrics);
    }

    /**
     * Internal method for obtaining {@link Metrics} via {@code profile_id} from
     * {@link MetricsRepository}.
     * 
     * @param id The profileId to search within the repository.
     * @return The found {@link Metrics}.
     */
    private Metrics findById(UUID id) throws IdNotFoundException {
        return metricsRepository.findById(id)
            .orElseThrow(IdNotFoundException::new);
    }

}

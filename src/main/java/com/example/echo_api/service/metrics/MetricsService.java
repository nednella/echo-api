package com.example.echo_api.service.metrics;

import java.util.UUID;

import com.example.echo_api.exception.custom.account.IdNotFoundException;
import com.example.echo_api.persistence.model.profile.Metrics;

public interface MetricsService {

    /**
     * Fetches profile {@link Metrics} via {@code profile_id}.
     * 
     * @param profileId The profile id of the metrics to fetch.
     * @return profile {@link Metrics}.
     * @throws IdNotFoundException If the id is not found.
     */
    public Metrics getMetrics(UUID profileId) throws IdNotFoundException;

    /**
     * Updates profile {@link Metrics} by incrementing {@code followingCount}.
     * 
     * @param profileId The profile id of the metrics to update.
     * @throws IdNotFoundException If the id is not found.
     */
    public void incrementFollowing(UUID profileId) throws IdNotFoundException;

    /**
     * Updates profile {@link Metrics} by incrementing {@code followerCount}.
     * 
     * @param profileId The profile id of the metrics to update.
     * @throws IdNotFoundException If the id is not found.
     */
    public void incrementFollowers(UUID profileId) throws IdNotFoundException;

    /**
     * Updates profile {@link Metrics} by incrementing {@code postCount}.
     * 
     * @param profileId The profile id of the metrics to update.
     * @throws IdNotFoundException If the id is not found.
     */
    public void incrementPosts(UUID profileId) throws IdNotFoundException;

    /**
     * Updates profile {@link Metrics} by incrementing {@code mediaCount}.
     * 
     * @param profileId The profile id of the metrics to update.
     * @throws IdNotFoundException If the id is not found.
     */
    public void incrementMedia(UUID profileId) throws IdNotFoundException;

    /**
     * Updates profile {@link Metrics} by decrementing {@code followingCount}.
     * 
     * @param profileId The profile id of the metrics to update.
     * @throws IdNotFoundException If the id is not found.
     */
    public void decrementFollowing(UUID profileId) throws IdNotFoundException;

    /**
     * Updates profile {@link Metrics} by decrementing {@code followerCount}.
     * 
     * @param profileId The profile id of the metrics to update.
     * @throws IdNotFoundException If the id is not found.
     */
    public void decrementFollowers(UUID profileId) throws IdNotFoundException;

    /**
     * Updates profile {@link Metrics} by decrementing {@code postCount}.
     * 
     * @param profileId The profile id of the metrics to update.
     * @throws IdNotFoundException If the id is not found.
     */
    public void decrementPosts(UUID profileId) throws IdNotFoundException;

    /**
     * Updates profile {@link Metrics} by decrementing {@code mediaCount}.
     * 
     * @param profileId The profile id of the metrics to update.
     * @throws IdNotFoundException If the id is not found.
     */
    public void decrementMedia(UUID profileId) throws IdNotFoundException;

}

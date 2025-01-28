package com.example.echo_api.service.metrics;

import java.util.UUID;

import com.example.echo_api.exception.custom.account.IdNotFoundException;
import com.example.echo_api.persistence.model.profile.Metrics;

public interface MetricsService {

    /**
     * Fetches profile {@link Metrics} via {@code profile_id}.
     * 
     * @param profileId The profile id.
     * @return profile {@link Metrics}.
     * @throws IdNotFoundException If the id is not found.
     */
    public Metrics getMetrics(UUID profileId) throws IdNotFoundException;

}

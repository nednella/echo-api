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

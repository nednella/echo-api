package com.example.echo_api.persistence.repository;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.example.echo_api.persistence.model.profile.Metrics;

public interface MetricsRepository extends ListCrudRepository<Metrics, UUID> {

}

package com.example.echo_api.persistence.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.example.echo_api.persistence.model.profile.Profile;

public interface ProfileRepository extends ListCrudRepository<Profile, UUID> {

    Optional<Profile> findByUsername(String username);

}

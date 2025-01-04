package com.example.echo_api.repository;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import com.example.echo_api.model.User;
import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

}

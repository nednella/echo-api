package com.example.echo_api.integration.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.echo_api.persistence.model.User;
import com.example.echo_api.persistence.repository.UserRepository;

/**
 * Integration test class for {@link UserRepository}.
 */
@DataJpaTest
class UserRepositoryTest extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Saves a {@link User} object to the {@link UserRepository} before each test.
     */
    @BeforeEach
    public void setUp() {
        testUser = new User("test", "password1");
        userRepository.save(testUser);
    }

    /**
     * Tests the {@link UserRepository#findByUsername(String)} method to verify that
     * a user can be found by their username.
     */
    @Test
    void UserRepository_FindByUsername_ReturnUser() {
        Optional<User> foundUser = userRepository.findByUsername(testUser.getUsername());

        assertNotNull(foundUser);
        assertTrue(foundUser.isPresent());
        assertEquals(testUser, foundUser.get());
    }

    /**
     * Tests the {@link UserRepository#findByUsername(String)} method to verify that
     * searching for a non-existent user returns an empty result.
     */
    @Test
    void UserRepository_FindByUsername_ReturnEmpty() {
        Optional<User> foundUser = userRepository.findByUsername("nonExistentUser");

        assertNotNull(foundUser);
        assertTrue(foundUser.isEmpty());
    }

    /**
     * Tests the {@link UserRepository#existsByUsername(String)} method to verify
     * that the repository correctly identifies that a user exists when searching
     * for a valid username.
     */
    @Test
    void UserRepository_ExistsByUsername_ReturnTrue() {
        boolean exists = userRepository.existsByUsername(testUser.getUsername());

        assertTrue(exists);
    }

    /**
     * Tests the {@link UserRepository#existsByUsername(String)} method to verify
     * that the repository correctly identifies that a user does not exist when
     * searching for a non-existent username.
     */
    @Test
    void UserRepository_ExistsByUsername_ReturnFalse() {
        boolean exists = userRepository.existsByUsername("nonExistentUser");

        assertFalse(exists);
    }

}

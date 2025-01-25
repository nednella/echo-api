package com.example.echo_api.integration.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.echo_api.integration.util.RepositoryTest;
import com.example.echo_api.persistence.model.user.User;
import com.example.echo_api.persistence.repository.UserRepository;

/**
 * Integration test class for {@link UserRepository}.
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryIT extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * Save a {@link User} object to the {@link UserRepository} before commencing.
     */
    @BeforeAll
    void setUp() {
        testUser = new User("test", "password1");
        userRepository.save(testUser);
    }

    /**
     * Test the {@link UserRepository#findByUsername(String)} method to verify that
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
     * Test the {@link UserRepository#findByUsername(String)} method to verify that
     * searching for a non-existent user returns an empty result.
     */
    @Test
    void UserRepository_FindByUsername_ReturnEmpty() {
        Optional<User> foundUser = userRepository.findByUsername("nonExistentUser");

        assertNotNull(foundUser);
        assertTrue(foundUser.isEmpty());
    }

    /**
     * Test the {@link UserRepository#existsByUsername(String)} method to verify
     * that the repository correctly identifies that a user exists when searching
     * for a valid username.
     */
    @Test
    void UserRepository_ExistsByUsername_ReturnTrue() {
        boolean exists = userRepository.existsByUsername(testUser.getUsername());

        assertTrue(exists);
    }

    /**
     * Test the {@link UserRepository#existsByUsername(String)} method to verify
     * that the repository correctly identifies that a user does not exist when
     * searching for a non-existent username.
     */
    @Test
    void UserRepository_ExistsByUsername_ReturnFalse() {
        boolean exists = userRepository.existsByUsername("nonExistentUser");

        assertFalse(exists);
    }

}

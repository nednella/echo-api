package com.example.echo_api.integration.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import com.example.echo_api.integration.util.RepositoryTest;
import com.example.echo_api.persistence.model.profile.Profile;
import com.example.echo_api.persistence.model.user.User;
import com.example.echo_api.persistence.repository.ProfileRepository;
import com.example.echo_api.persistence.repository.UserRepository;

/**
 * Integration test class for {@link ProfileRepository}.
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class ProfileRepositoryIT extends RepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    private Profile profile;

    /**
     * Save a {@link Profile} object to {@link ProfileRepository}.
     * 
     * <p>
     * Requires a {@link User} object saved to {@link UserRepository} to set the
     * {@code user_id} field.
     */
    @BeforeAll
    void setup() {
        User user = new User("test", "test");
        userRepository.save(user);

        profile = new Profile(user);
        profileRepository.save(profile);
    }

    /**
     * Test {@link ProfileRepository#findByUsername(String)} to verify a profile can
     * be found by their username.
     */
    @Test
    void ProfileRepository_FindByUsername_ReturnProfile() {
        Optional<Profile> foundProfile = profileRepository.findByUsername(profile.getUsername());

        assertNotNull(foundProfile);
        assertTrue(foundProfile.isPresent());
    }

    /**
     * Test {@link ProfileRepository#findByUsername(String)} to verify that
     * searching for a non-existent profile returns an empty result.
     */
    @Test
    void ProfileRepository_FindByUsername_ReturnEmpty() {
        Optional<Profile> foundProfile = profileRepository.findByUsername("non-existent-username");

        assertNotNull(foundProfile);
        assertTrue(foundProfile.isEmpty());
    }

}

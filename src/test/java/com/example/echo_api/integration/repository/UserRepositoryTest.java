package com.example.echo_api.integration.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.echo_api.persistence.model.User;
import com.example.echo_api.persistence.repository.UserRepository;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @Test
    public void containerConnectionEstablished() {
        assertTrue(postgres.isCreated());
        assertTrue(postgres.isRunning());

    }

    @BeforeEach
    public void setUp() {
        // arrange
        testUser = new User("test", "123");
        userRepository.save(testUser);
    }

    @Test
    public void UserRepository_FindByUsername_ReturnUser() {
        // act
        Optional<User> foundUser = userRepository.findByUsername(testUser.getUsername());

        // assert
        assertNotNull(foundUser);
        assertTrue(foundUser.isPresent());
        assertEquals(testUser, foundUser.get());
    }

    @Test
    public void UserRepository_FindByUsername_ReturnEmpty() {
        // act
        Optional<User> foundUser = userRepository.findByUsername("nonExistentUser");

        // assert
        assertNotNull(foundUser);
        assertTrue(foundUser.isEmpty());
    }

    @Test
    public void UserRepository_ExistsByUsername_ReturnTrue() {
        // act
        boolean exists = userRepository.existsByUsername(testUser.getUsername());

        // assert
        assertNotNull(exists);
        assertTrue(exists);
    }

    @Test
    public void UserRepository_ExistsByUsername_ReturnFalse() {
        // act
        boolean exists = userRepository.existsByUsername("nonExistentUser");

        // assert
        assertNotNull(exists);
        assertFalse(exists);
    }

}

package com.example.echo_api.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.echo_api.exception.custom.UsernameAlreadyExistsException;
import com.example.echo_api.persistence.model.User;
import com.example.echo_api.persistence.repository.UserRepository;
import com.example.echo_api.service.user.UserService;
import com.example.echo_api.service.user.UserServiceImpl;

/**
 * Unit test class for {@link UserService}.
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    /**
     * Sets up a {@link User} object before each test.
     */
    @BeforeEach
    public void initUser() {
        testUser = new User(
                "testUsername",
                "testPassword");
    }

    /**
     * This test ensures that the {@link UserServiceImpl#findAll()} method correctly
     * returns a list of users from the repository.
     * 
     * <p>
     * Mocks the {@link UserRepository#findAll()} method to return a list of users.
     * 
     */
    @Test
    public void UserService_FindAll_ReturnListOfUser() {
        // arrange mock behaviour
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        // act
        List<User> users = userService.findAll();

        // assert
        assertEquals(1, users.size());
        assertIterableEquals(List.of(testUser), users);

        // verify
        verify(userRepository, times(1)).findAll();
    }

    /**
     * This test ensures that the {@link UserServiceImpl#findAll()} method correctly
     * returns an empty list when no users exist.
     * 
     * <p>
     * Mocks the {@link UserRepository#findAll()} method to return an empty list.
     * 
     */
    @Test
    public void UserService_FindAll_ReturnListOfEmpty() {
        // arrange mock behaviour
        when(userRepository.findAll()).thenReturn(List.of());

        // act
        List<User> users = userService.findAll();

        // assert
        assertEquals(0, users.size());
        assertIterableEquals(List.of(), users);

        // verify
        verify(userRepository, times(1)).findAll();
    }

    /**
     * This test ensures that the {@link UserServiceImpl#findByUsername(String)}
     * method correctly returns the user when the username exists.
     * 
     * <p>
     * Mocks the {@link UserRepository#findByUsername(String)} method to return a
     * user.
     * 
     */
    @Test
    public void UserService_FindByUsername_ReturnUser() {
        // arrange mock behaviour
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.of(testUser));

        // act
        User foundUser = userService.findByUsername(testUser.getUsername());

        // assert
        assertEquals(testUser, foundUser);

        // verify
        verify(userRepository, times(1)).findByUsername(testUser.getUsername());
    }

    /**
     * This test ensures that the {@link UserServiceImpl#findByUsername(String)}
     * method throws a {@link UsernameNotFoundException} when the username does not
     * exist.
     * 
     * <p>
     * Mocks the {@link UserRepository#findByUsername(String)} method to throw a
     * {@link UsernameNotFoundException}.
     * 
     */
    @Test
    public void UserService_FindByUsername_ThrowUsernameNotFound() {
        // arrange mock behaviour
        when(userRepository.findByUsername(testUser.getUsername())).thenThrow(UsernameNotFoundException.class);

        // act & assert
        assertThrows(UsernameNotFoundException.class,
                () -> userService.findByUsername(testUser.getUsername()));

        // verify
        verify(userRepository, times(1)).findByUsername(testUser.getUsername());
    }

    /**
     * This test ensures that the {@link UserServiceImpl#existsByUsername(String)}
     * method returns true when the username exists in the repository.
     * 
     * <p>
     * Mocks the {@link UserRepository#existsByUsername(String)} method to return
     * true.
     * 
     */
    @Test
    public void UserService_ExistsByUsername_ReturnTrue() {
        // arrange mock behaviour
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);

        // act
        boolean exists = userService.existsByUsername(testUser.getUsername());

        // assert
        assertTrue(exists);

        // verify
        verify(userRepository, times(1)).existsByUsername(testUser.getUsername());
    }

    /**
     * This test ensures that the {@link UserServiceImpl#existsByUsername(String)}
     * method returns false when the username does not exist in the repository.
     * 
     * <p>
     * Mocks the {@link UserRepository#existsByUsername(String)} method to return
     * false.
     * 
     */
    @Test
    public void UserService_ExistsByUsername_ReturnFalse() {
        // arrange mock behaviour
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);

        // act
        boolean exists = userService.existsByUsername(testUser.getUsername());

        // assert
        assertFalse(exists);

        // verify
        verify(userRepository, times(1)).existsByUsername(testUser.getUsername());
    }

    /**
     * This test ensures that the {@link UserServiceImpl#createUser(String, String)}
     * method correctly creates a new user when the username does not already exist.
     * 
     * <p>
     * Mocks the {@link UserRepository#save(User)} method to return the saved user.
     * 
     * <p>
     * Mocks the {@link UserRepository#existsByUsername(String)} to return false.
     * 
     * <p>
     * Mocks the {@link PasswordEncoder#encode(CharSequence)} to return the string
     * "encodedPassword".
     * 
     */
    @Test
    public void UserService_CreateUser_ReturnVoid() {
        // arrange mock behaviour
        when(userRepository.save(testUser)).thenReturn(testUser);
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // act
        userService.createUser(testUser.getUsername(), testUser.getPassword());

        // verify
        verify(userRepository, times(1)).save(testUser);
        verify(passwordEncoder, times(1)).encode(testUser.getPassword());
    }

    /**
     * This test ensures that the {@link UserServiceImpl#createUser(String, String)}
     * method throws a {@link UsernameAlreadyExistsException} when the username
     * already exists.
     * 
     * <p>
     * Mocks the {@link UserRepository#existsByUsername(String)} method to return
     * true.
     * 
     */
    @Test
    public void UserService_CreateUser_ThrowUsernameAlreadyExists() {
        // arrange mock behaviour
        when(userRepository.existsByUsername(testUser.getUsername())).thenReturn(true);

        // act & assert
        assertThrows(UsernameAlreadyExistsException.class,
                () -> userService.createUser(testUser.getUsername(), testUser.getPassword()));

        // verify
        verify(userRepository, times(1)).existsByUsername(testUser.getUsername());
    }

}

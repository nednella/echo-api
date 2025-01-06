package com.example.echo_api.service.user;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.echo_api.exception.user.UserAlreadyExistsException;
import com.example.echo_api.exception.user.UserNotFoundException;
import com.example.echo_api.persistence.model.User;
import com.example.echo_api.persistence.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void createUser(String username, String password, String role) throws UserAlreadyExistsException {
        if (existsByUsername(username)) {
            throw new UserAlreadyExistsException(username);
        }

        User user = new User(username, passwordEncoder.encode(password), role);
        userRepository.save(user);
    }

    @Override
    public void enableUser(String username) throws UserAlreadyExistsException {
        User user = findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void disableUser(String username) throws UserAlreadyExistsException {
        User user = findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public void updateUsername(String username, String newUsername) throws UserNotFoundException {
        User user = findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        user.setUsername(newUsername);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(String username, String newPassword) throws UserNotFoundException {
        User user = findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        user.setPassword(newPassword);
        userRepository.save(user);
    }

}

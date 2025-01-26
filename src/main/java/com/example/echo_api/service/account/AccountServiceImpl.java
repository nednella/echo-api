package com.example.echo_api.service.account;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.echo_api.exception.custom.password.IncorrectCurrentPasswordException;
import com.example.echo_api.exception.custom.password.PasswordException;
import com.example.echo_api.exception.custom.username.UsernameAlreadyExistsException;
import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.exception.custom.username.UsernameNotFoundException;
import com.example.echo_api.persistence.dto.request.account.UpdatePasswordRequest;
import com.example.echo_api.persistence.model.user.Role;
import com.example.echo_api.persistence.model.user.User;
import com.example.echo_api.persistence.repository.UserRepository;
import com.example.echo_api.service.profile.ProfileService;
import com.example.echo_api.service.session.SessionService;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing CRUD operations of a {@link User}.
 * 
 * @see ProfileService
 * @see SessionService
 * @see UserRepository
 * @see PasswordEncoder
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final ProfileService profileService;
    private final SessionService sessionService;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(String username, String password) throws UsernameException {
        return registerWithRole(username, password, Role.USER);
    }

    @Override
    public User registerWithRole(String username, String password, Role role) throws UsernameException {
        if (existsByUsername(username)) {
            throw new UsernameAlreadyExistsException();
        }

        User user = new User(username, passwordEncoder.encode(password), role);
        userRepository.save(user);
        profileService.registerForUser(user);

        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) throws UsernameException {
        return userRepository.findByUsername(username)
            .orElseThrow(UsernameNotFoundException::new);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void updateUsername(String username) throws UsernameException {
        if (existsByUsername(username)) {
            throw new UsernameAlreadyExistsException();
        }

        User user = sessionService.getAuthenticatedUser();
        user.setUsername(username);

        userRepository.save(user);
        sessionService.reauthenticate(user);
    }

    @Override
    public void updatePassword(UpdatePasswordRequest request) throws PasswordException {
        User user = sessionService.getAuthenticatedUser();

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IncorrectCurrentPasswordException();
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(user);
        sessionService.reauthenticate(user);
    }

}

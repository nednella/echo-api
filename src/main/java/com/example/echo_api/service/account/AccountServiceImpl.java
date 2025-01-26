package com.example.echo_api.service.account;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.echo_api.exception.custom.password.IncorrectCurrentPasswordException;
import com.example.echo_api.exception.custom.password.PasswordException;
import com.example.echo_api.exception.custom.username.UsernameAlreadyExistsException;
import com.example.echo_api.exception.custom.username.UsernameException;
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

        User newUser = new User(username, passwordEncoder.encode(password), role);
        userRepository.save(newUser);
        profileService.registerForUser(newUser);

        return newUser;
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return !existsByUsername(username);
    }

    @Override
    public void updateUsername(String username) throws UsernameException {
        if (existsByUsername(username)) {
            throw new UsernameAlreadyExistsException();
        }

        User me = getMe();
        me.setUsername(username);

        userRepository.save(me);
        sessionService.reauthenticate(me);
    }

    @Override
    public void updatePassword(UpdatePasswordRequest request) throws PasswordException {
        User me = getMe();

        if (!passwordEncoder.matches(request.currentPassword(), me.getPassword())) {
            throw new IncorrectCurrentPasswordException();
        }

        me.setPassword(passwordEncoder.encode(request.newPassword()));

        userRepository.save(me);
        sessionService.reauthenticate(me);
    }

    /**
     * Internal method for checking whether a {@link User} exists with the supplied
     * {@code username}.
     * 
     * @param username The username to check against.
     * @return A boolean indicating a user's existence.
     */
    private boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Internal method for obtaining a {@link User} associated to the authenticated
     * user.
     * 
     * @return The found {@link User}.
     */
    private User getMe() {
        return sessionService.getAuthenticatedUser();
    }

}

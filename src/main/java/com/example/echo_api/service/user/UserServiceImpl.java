package com.example.echo_api.service.user;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.echo_api.exception.custom.password.ConfirmationPasswordMismatchException;
import com.example.echo_api.exception.custom.password.IncorrectCurrentPasswordException;
import com.example.echo_api.exception.custom.password.NewPasswordEqualsOldPasswordException;
import com.example.echo_api.exception.custom.password.PasswordException;
import com.example.echo_api.exception.custom.username.UsernameAlreadyExistsException;
import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.exception.custom.username.UsernameNotFoundException;
import com.example.echo_api.persistence.dto.request.account.UpdatePasswordRequest;
import com.example.echo_api.persistence.model.User;
import com.example.echo_api.persistence.repository.UserRepository;
import com.example.echo_api.service.auth.AuthService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;

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
    public void createUser(String username, String password, String role) throws UsernameException {
        if (existsByUsername(username)) {
            throw new UsernameAlreadyExistsException();
        }

        User user = new User(username, passwordEncoder.encode(password), role);
        userRepository.save(user);
    }

    @Override
    public void updateUsername(String username) throws UsernameException {
        if (existsByUsername(username)) {
            throw new UsernameAlreadyExistsException();
        }

        User user = authService.getAuthenticatedUser();
        user.setUsername(username);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(UpdatePasswordRequest request) throws PasswordException {
        // validate new == confirmation
        if (!request.newPassword().equals(request.confirmationPassword())) {
            throw new ConfirmationPasswordMismatchException();
        }

        // validate new != current
        if (request.newPassword().equals(request.currentPassword())) {
            throw new NewPasswordEqualsOldPasswordException();
        }

        // validate current password
        User user = authService.getAuthenticatedUser();
        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IncorrectCurrentPasswordException();
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }

}

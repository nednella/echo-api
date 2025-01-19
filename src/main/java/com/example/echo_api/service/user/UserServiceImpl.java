package com.example.echo_api.service.user;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.echo_api.exception.custom.username.UsernameAlreadyExistsException;
import com.example.echo_api.exception.custom.username.UsernameException;
import com.example.echo_api.exception.custom.username.UsernameNotFoundException;
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

}

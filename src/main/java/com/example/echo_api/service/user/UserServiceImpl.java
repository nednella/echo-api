package com.example.echo_api.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.echo_api.exception.user.UserNotFoundException;
import com.example.echo_api.model.User;
import com.example.echo_api.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

}

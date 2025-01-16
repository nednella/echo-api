package com.example.echo_api.loader;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.echo_api.persistence.model.User;
import com.example.echo_api.service.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserLoader extends AbstractJsonLoader<User> {

    private static final String path = "data/users.json";

    private final UserService userService;

    @Override
    protected String getFilePath() {
        return path;
    }

    @Override
    protected void loadData() throws IOException {
        if (userService.findAll().isEmpty()) {
            log.info("USER table is empty. Populating table from path: {}", getFilePath());
            List<User> entities = loadJsonFromResourceFile(getFilePath(), User.class);
            saveToRepository(entities);
        } else {
            log.info("USER table is not empty. Skipping data population.");
        }
    }

    @Override
    protected void saveToRepository(List<User> entities) {
        for (User user : entities) {
            try {
                userService.createUser(user.getUsername(), user.getPassword(), user.getRole());
            } catch (Exception ex) {
                log.error("Failed to create user: {}. Reason: {}", user.getUsername(), ex.getMessage(), ex);
            }
        }
    }

}

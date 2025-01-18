package com.example.echo_api.unit.config;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.echo_api.config.RegexConfig;

public class RegexConfigTest {

    private static List<String> validUsernames;
    private static List<String> invalidUsernames;

    private static List<String> validPasswords;
    private static List<String> invalidPasswords;

    @BeforeAll
    public static void readFiles() {
        try {
            validUsernames = readFile("data/valid_usernames.txt");
            invalidUsernames = readFile("data/invalid_usernames.txt");

            validPasswords = readFile("data/valid_passwords.txt");
            invalidPasswords = readFile("data/invalid_passwords.txt");
        } catch (IOException ex) {
            throw new RuntimeException("Error reading from files", ex);
        }
    }

    private static List<String> readFile(String fileName) throws IOException {
        InputStream inputStream = RegexConfigTest.class.getClassLoader().getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new FileNotFoundException("File not found in resources: " + fileName);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            return reader.lines().toList();
        }
    }

    @Test
    void RegexConfig_Username_ShouldPass() {
        for (String username : validUsernames) {
            assertTrue(username.matches(RegexConfig.USERNAME),
                "Expected username to match regex: " + username);
        }
    }

    @Test
    void RegexConfig_Username_ShouldFail() {
        for (String username : invalidUsernames) {
            assertFalse(username.matches(RegexConfig.USERNAME),
                "Expected username to match regex: " + username);
        }
    }

    @Test
    void RegexConfig_Password_ShouldPass() {
        for (String password : validPasswords) {
            assertTrue(password.matches(RegexConfig.PASSWORD),
                "Expected password to match regex: " + password);
        }
    }

    @Test
    void RegexConfig_Password_ShouldFail() {
        for (String password : invalidPasswords) {
            assertFalse(password.matches(RegexConfig.PASSWORD),
                "Expected password to match regex: " + password);
        }
    }

}

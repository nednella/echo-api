package com.example.echo_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.echo_api.model.User;
import com.example.echo_api.repository.UserRepository;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, PasswordEncoder encoder) {
		return args -> {
			if (!userRepository.existsByUsername("admin")) {
				userRepository.save(new User("admin", encoder.encode("password")));
			}
		};
	}

}

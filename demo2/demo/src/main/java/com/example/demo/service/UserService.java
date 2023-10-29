package com.example.demo.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;

import ch.qos.logback.classic.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

	private final UserRepository userRepository;

	public UserEntity create(UserEntity userEntity) {
		if (userEntity == null || userEntity.getUsername() == null) {
			throw new RuntimeException("Invalid arguments");
		}
		final String username = userEntity.getUsername();
		if (userRepository.existsByUsername(username)) {
			log.warn("Username already exists{}", username);
			throw new RuntimeException("Username already exists");
		}

		return userRepository.save(userEntity);
	}

	public UserEntity getByCredentials(final String username, final String password,
			final PasswordEncoder passwordEncoder) {
		
		final UserEntity originalUser = userRepository.findByUsername(username);

		if (originalUser != null && passwordEncoder.matches(password, originalUser.getPassword())) {
			return originalUser;
		}

		return null;
	}
}

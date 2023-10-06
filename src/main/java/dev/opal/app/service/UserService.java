package dev.opal.app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dev.opal.app.codegen.model.UsersResponse;
import dev.opal.app.entity.AccessUser;
import dev.opal.app.exceptions.UserAlreadyExistsException;
import dev.opal.app.exceptions.UsersRetrievalException;
import dev.opal.app.mapper.UsersMapper;
import dev.opal.app.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final Logger logger = LoggerFactory.getLogger(UserService.class);

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UsersResponse getAllUsers() {
		try {
			return UsersMapper.toUsersResponse(userRepository.findAll());
		} catch (Exception e) {
			logger.error("Error fetching all users", e);
			throw new UsersRetrievalException("Failed to retrieve users from database");
		}
	}

	public AccessUser createUser(String email) {
		try {
			if (userRepository.findByEmail(email).isPresent()) {
				logger.error("User already exists with email: {}", email);
				throw new UserAlreadyExistsException("User with email: " + email + " already exists.");
			}
			AccessUser user = new AccessUser(email);
			return userRepository.saveAndFlush(user);
		} catch (UserAlreadyExistsException e) {
			logger.error("User already exists with email: {}", email, e);
			throw e;
		} catch (Exception e) {
			logger.error("Error creating user with email: {}", email, e);
			throw e;
		}
	}
}
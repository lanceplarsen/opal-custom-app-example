package dev.opal.app.service;

import org.springframework.stereotype.Service;

import dev.opal.app.entity.User;
import dev.opal.app.mapper.UsersMapper;
import dev.opal.app.model.UsersResponse;
import dev.opal.app.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public UsersResponse getAllUsers() {
		return UsersMapper.toUsersResponse(userRepository.findAll());
	}

	public User createUser(String email) {
		User user = new User();
		user.setEmail(email);
		return userRepository.saveAndFlush(user);
	}

}
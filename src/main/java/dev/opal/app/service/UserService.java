package dev.opal.app.service;

import org.springframework.stereotype.Service;

import dev.opal.app.codegen.model.UsersResponse;
import dev.opal.app.entity.AccessUser;
import dev.opal.app.mapper.UsersMapper;
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

	public AccessUser createUser(String email) {
		AccessUser user = new AccessUser(email);
		return userRepository.saveAndFlush(user);
	}

}
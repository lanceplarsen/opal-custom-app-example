package dev.opal.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.opal.app.codegen.model.UsersResponse;
import dev.opal.app.service.UserService;

@RestController
@RequestMapping("/users")
public class UsersApiController {

	private final UserService userService;

	public UsersApiController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<UsersResponse> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@PostMapping
	public ResponseEntity<String> createUser(@RequestParam String email) {
		userService.createUser(email);
		return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
	}
}
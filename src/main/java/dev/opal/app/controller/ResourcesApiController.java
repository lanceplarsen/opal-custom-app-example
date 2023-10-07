package dev.opal.app.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.opal.app.codegen.model.AddResourceUserRequest;
import dev.opal.app.codegen.model.ResourceResponse;
import dev.opal.app.codegen.model.ResourceUsersResponse;
import dev.opal.app.service.ResourceService;

@RestController
@RequestMapping("/resources")
public class ResourcesApiController {

	private final ResourceService resourceService;

	public ResourcesApiController(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@GetMapping()
	public ResponseEntity<?> getResources(@RequestParam String app_id) {
		return ResponseEntity.ok(resourceService.getAllResources());
	}

	@GetMapping("/{resource_id}")
	public ResponseEntity<?> getResourceById(@PathVariable String resource_id, @RequestParam String app_id) {
		Optional<ResourceResponse> resource = resourceService.getResourceById(resource_id);
		if (resource.isPresent()) {
			return ResponseEntity.ok(resource.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{resource_id}/users")
	public ResponseEntity<?> getResourceUsersById(@PathVariable String resource_id, @RequestParam String app_id) {
		Optional<ResourceUsersResponse> resourceUsers = resourceService.getResourceUsersById(resource_id);
		if (resourceUsers.isPresent()) {
			return ResponseEntity.ok(resourceUsers.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// TODO Add Access Level Support
	@GetMapping("/{resource_id}/access_levels")
	public ResponseEntity<?> getAccessLevelsForResource(@PathVariable String resource_id, @RequestParam String app_id) {
		return ResponseEntity.ok(resourceService.getAccessLevelsForResource(resource_id));
	}

	@PostMapping
	public ResponseEntity<String> createResource(String name, String description) {
		resourceService.createResource(name, description);
		return new ResponseEntity<>("Resource created successfully", HttpStatus.CREATED);
	}

	@PostMapping("/{resource_id}/users")
	public ResponseEntity<?> addUserToResource(@PathVariable("resource_id") String resourceId,
			@RequestBody AddResourceUserRequest request) {
		resourceService.addUserToResource(resourceId, request);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{resource_id}/users/{user_id}")
	public ResponseEntity<?> removeUserFromResource(@PathVariable("resource_id") String resourceId,
			@PathVariable("user_id") String userId, @RequestParam String app_id,
			@RequestParam(required = false) String access_level_id) {

		resourceService.removeUserFromResource(resourceId, userId, app_id, access_level_id);
		return ResponseEntity.ok().build();
	}
}
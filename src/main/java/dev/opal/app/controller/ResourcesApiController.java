package dev.opal.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.opal.app.codegen.model.AddResourceUserRequest;
import dev.opal.app.codegen.model.ResourceAccessLevelsResponse;
import dev.opal.app.codegen.model.ResourceResponse;
import dev.opal.app.codegen.model.ResourceUsersResponse;
import dev.opal.app.codegen.model.ResourcesResponse;
import dev.opal.app.service.ResourceService;

@RestController
@RequestMapping("/resources")
public class ResourcesApiController {

	private final ResourceService resourceService;
	private final Logger logger = LoggerFactory.getLogger(ResourcesApiController.class);

	public ResourcesApiController(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	@GetMapping()
	public ResponseEntity<ResourcesResponse> getResources() {
		return ResponseEntity.ok(resourceService.getAllResources());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResourceResponse> getResourceById(@PathVariable String id) {
		return resourceService.getResourceById(id).map(ResponseEntity::ok).orElseGet(() -> {
			logger.warn("Resource with ID {} not found", id);
			return ResponseEntity.notFound().build();
		});
	}

	@GetMapping("/{id}/users")
	public ResponseEntity<ResourceUsersResponse> getResourceUsersById(@PathVariable String id) {
		return resourceService.getResourceUsersById(id).map(ResponseEntity::ok).orElseGet(() -> {
			logger.warn("Resource with ID {} not found", id);
			return ResponseEntity.notFound().build();
		});
	}

	@GetMapping("/{resource_id}/access_levels")
	public ResponseEntity<ResourceAccessLevelsResponse> getAccessLevelsForResource(@PathVariable String resource_id) {
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
		if (resourceService.addUserToResource(resourceId, request)) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}
}
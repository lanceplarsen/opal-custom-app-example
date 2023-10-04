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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.opal.app.codegen.model.AddGroupResourceRequest;
import dev.opal.app.codegen.model.AddGroupUserRequest;
import dev.opal.app.codegen.model.GroupResourcesResponse;
import dev.opal.app.codegen.model.GroupResponse;
import dev.opal.app.codegen.model.GroupUsersResponse;
import dev.opal.app.codegen.model.GroupsResponse;
import dev.opal.app.service.GroupsService;

@RestController
@RequestMapping("/groups")
public class GroupsApiController {

	private final GroupsService groupsService;
	private final Logger logger = LoggerFactory.getLogger(GroupsApiController.class);

	public GroupsApiController(GroupsService groupsService) {
		this.groupsService = groupsService;
	}

	@GetMapping()
	public ResponseEntity<GroupsResponse> getGroups(@RequestParam String app_id) {
		return ResponseEntity.ok(groupsService.getAllGroups());
	}

	@PostMapping()
	public ResponseEntity<String> createGroup(String name, String description) {
		groupsService.createGroup(name, description);
		return new ResponseEntity<>("Group created successfully", HttpStatus.CREATED);
	}

	@GetMapping("/{group_id}")
	public ResponseEntity<GroupResponse> getGroupById(@PathVariable String group_id) {
		return groupsService.getGroupById(group_id).map(ResponseEntity::ok).orElseGet(() -> {
			logger.warn("Group with ID {} not found", group_id);
			return ResponseEntity.notFound().build();
		});
	}

	@GetMapping("/{group_id}/users")
	public ResponseEntity<GroupUsersResponse> getUsersForGroup(@PathVariable String group_id,
			@RequestParam String app_id) {
		return groupsService.getUsersForGroup(group_id).map(ResponseEntity::ok).orElseGet(() -> {
			logger.warn("Group with ID {} not found", group_id);
			return ResponseEntity.notFound().build();
		});
	}

	@GetMapping("/{group_id}/resources")
	public ResponseEntity<GroupResourcesResponse> getResourcesForGroup(@PathVariable String group_id,
			@RequestParam String app_id) {
		return groupsService.getResourcesForGroup(group_id).map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/{group_id}/users")
	public ResponseEntity<?> addUserToGroup(@PathVariable String group_id, @RequestBody AddGroupUserRequest request) {
		boolean success = groupsService.addUserToGroup(group_id, request);
		if (success) {
			return ResponseEntity.ok().build();
		}
		logger.warn("Unable to add user to group. Group ID: {}, User ID: {}", group_id, request.getUserId());
		return ResponseEntity.badRequest().build();
	}

	@PostMapping("/{group_id}/resources")
	public ResponseEntity<?> addResourceToGroup(@PathVariable String group_id,
			@RequestBody AddGroupResourceRequest request) {
		boolean success = groupsService.addResourceToGroup(group_id, request);
		if (success) {
			return ResponseEntity.ok().build();
		}
		logger.warn("Unable to add resource to group. Group ID: {}, Resource ID: {}", group_id,
				request.getResourceId());
		return ResponseEntity.badRequest().build();
	}
}
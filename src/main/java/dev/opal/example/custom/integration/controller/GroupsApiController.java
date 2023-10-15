package dev.opal.example.custom.integration.controller;

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

import dev.opal.example.custom.integration.codegen.connector.model.AddGroupResourceRequest;
import dev.opal.example.custom.integration.codegen.connector.model.AddGroupUserRequest;
import dev.opal.example.custom.integration.codegen.connector.model.Error;
import dev.opal.example.custom.integration.service.GroupsService;

@RestController
@RequestMapping("/groups")
public class GroupsApiController {

	private final GroupsService groupsService;

	public GroupsApiController(GroupsService groupsService) {
		this.groupsService = groupsService;
	}

	@GetMapping()
	public ResponseEntity<?> getGroups(@RequestParam String app_id) {
		return ResponseEntity.ok(groupsService.getAllGroups());
	}

	@PostMapping()
	public ResponseEntity<String> createGroup(String name, String description) {
		groupsService.createGroup(name, description);
		return new ResponseEntity<>("Group created successfully", HttpStatus.CREATED);
	}

	@GetMapping("/{group_id}")
	public ResponseEntity<?> getGroupById(@PathVariable String group_id, @RequestParam String app_id) {
		return ResponseEntity.ok(groupsService.getGroupById(group_id));
	}

	@GetMapping("/{group_id}/users")
	public ResponseEntity<?> getUsersForGroup(@PathVariable String group_id, @RequestParam String app_id) {
		return ResponseEntity.ok(groupsService.getUsersForGroup(group_id));
	}

	@GetMapping("/{group_id}/resources")
	public ResponseEntity<?> getResourcesForGroup(@PathVariable String group_id, @RequestParam String app_id) {
		return ResponseEntity.ok(groupsService.getResourcesForGroup(group_id));
	}

	@PostMapping("/{group_id}/users")
	public ResponseEntity<?> addUserToGroup(@PathVariable String group_id, @RequestBody AddGroupUserRequest request) {
		if (groupsService.addUserToGroup(group_id, request)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().body(new Error(400, "Failed to add user to group"));
		}
	}

	@PostMapping("/{group_id}/resources")
	public ResponseEntity<?> addResourceToGroup(@PathVariable String group_id,
			@RequestBody AddGroupResourceRequest request) {
		if (groupsService.addResourceToGroup(group_id, request)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().body(new Error(400, "Failed to add resource to group"));
		}
	}

	@DeleteMapping("/{group_id}/users/{user_id}")
	public ResponseEntity<?> removeUserFromGroup(@PathVariable String group_id, @PathVariable String user_id,
			@RequestParam String app_id) {
		if (groupsService.removeUserFromGroup(group_id, user_id)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().body(new Error(400, "Failed to remove user from group"));
		}
	}

	@DeleteMapping("/{group_id}/resources/{resource_id}")
	public ResponseEntity<?> removeResourceFromGroup(@PathVariable String group_id, @PathVariable String resource_id,
			@RequestParam String app_id, @RequestParam(required = false) String access_level_id) {
		// TODO add access level support
		if (groupsService.removeResourceFromGroup(group_id, resource_id, access_level_id)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().body(new Error(400, "Failed to remove resource from group"));
		}
	}

}
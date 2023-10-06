package dev.opal.app.controller;

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
import dev.opal.app.codegen.model.Error;
import dev.opal.app.service.GroupsService;

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

	// TODO Implement DELETE endpoints for user and resource removal from the group
}
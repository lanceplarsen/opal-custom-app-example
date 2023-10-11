package dev.opal.app.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dev.opal.app.codegen.model.AddGroupResourceRequest;
import dev.opal.app.codegen.model.AddGroupUserRequest;
import dev.opal.app.codegen.model.GroupResourcesResponse;
import dev.opal.app.codegen.model.GroupResponse;
import dev.opal.app.codegen.model.GroupUsersResponse;
import dev.opal.app.codegen.model.GroupsResponse;
import dev.opal.app.entity.AccessGroup;
import dev.opal.app.entity.AccessResource;
import dev.opal.app.entity.AccessRole;
import dev.opal.app.entity.AccessUser;
import dev.opal.app.entity.GroupAccessAssignment;
import dev.opal.app.exceptions.NotFoundException;
import dev.opal.app.mapper.GroupsMapper;
import dev.opal.app.repository.GroupAccessAssignmentRepository;
import dev.opal.app.repository.GroupRepository;
import dev.opal.app.repository.ResourceRepository;
import dev.opal.app.repository.RoleRepository;
import dev.opal.app.repository.UserRepository;

@Service
public class GroupsService {

	private final GroupRepository groupRepository;
	private final UserRepository userRepository;
	private final ResourceRepository resourceRepository;
	private final RoleRepository roleRepository;
	private final GroupAccessAssignmentRepository groupAccessAssignmentRepository;
	private final Logger logger = LoggerFactory.getLogger(GroupsService.class);

	public GroupsService(GroupRepository groupRepository, UserRepository userRepository,
			ResourceRepository resourceRepository, RoleRepository roleRepository,
			GroupAccessAssignmentRepository groupAccessAssignmentRepository) {
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
		this.resourceRepository = resourceRepository;
		this.roleRepository = roleRepository;
		this.groupAccessAssignmentRepository = groupAccessAssignmentRepository;
	}

	public GroupsResponse getAllGroups() {
		try {
			return GroupsMapper.toGroupsResponse(groupRepository.findAll());
		} catch (Exception e) {
			logger.error("Error fetching all groups", e);
			throw e;
		}
	}

	public void createGroup(String name, String description) {
		try {
			AccessGroup group = new AccessGroup(name, description);
			groupRepository.saveAndFlush(group);
		} catch (Exception e) {
			logger.error("Error creating group with name: {}", name, e);
			throw e;
		}
	}

	public GroupResponse getGroupById(String group_id) {
		try {
			return groupRepository.findById(group_id).map(GroupsMapper::toGroupResponse)
					.orElseThrow(() -> new NotFoundException("Group not found with id: " + group_id));
		} catch (NotFoundException e) {
			logger.error("Error fetching group by ID: {}", group_id, e);
			throw e;
		}
	}

	public GroupUsersResponse getUsersForGroup(String group_id) {
		try {
			AccessGroup group = groupRepository.findById(group_id)
					.orElseThrow(() -> new NotFoundException("Group not found with id: " + group_id));
			return GroupsMapper.toGroupUsersResponse(group.getUsers());
		} catch (NotFoundException e) {
			logger.error("Error fetching users for group ID: {}", group_id, e);
			throw e;
		}
	}

	public GroupResourcesResponse getResourcesForGroup(String group_id) {
		try {
			AccessGroup group = groupRepository.findById(group_id)
					.orElseThrow(() -> new NotFoundException("Group not found with id: " + group_id));
			;
			return GroupsMapper.toGroupResourcesResponse(group.getResourceAssignments());
		} catch (NotFoundException e) {
			logger.error("Error fetching resources for group ID: {}", group_id, e);
			throw e;
		}
	}

	public boolean addUserToGroup(String group_id, AddGroupUserRequest request) {
		try {
			AccessGroup group = groupRepository.findById(group_id)
					.orElseThrow(() -> new NotFoundException("Group not found with id: " + group_id));
			AccessUser user = userRepository.findById(request.getUserId())
					.orElseThrow(() -> new NotFoundException("User not found with id: " + request.getUserId()));
			group.getUsers().add(user);
			groupRepository.save(group);
			return true;
		} catch (NotFoundException e) {
			logger.error("Error adding user to group. Group ID: {}, User ID: {}", group_id, request.getUserId(), e);
			throw e;
		}
	}

	public boolean removeUserFromGroup(String group_id, String user_id) {
		try {
			AccessGroup group = groupRepository.findById(group_id)
					.orElseThrow(() -> new NotFoundException("Group not found with id: " + group_id));
			AccessUser user = userRepository.findById(user_id)
					.orElseThrow(() -> new NotFoundException("User not found with id: " + user_id));
			if (!group.getUsers().contains(user)) {
				throw new NotFoundException("User not found in the group with id: " + group_id);
			}
			group.getUsers().remove(user);
			groupRepository.save(group);
			return true;
		} catch (NotFoundException e) {
			logger.error("Error removing user from group. Group ID: {}, User ID: {}", group_id, user_id, e);
			throw e;
		}
	}

	public boolean addResourceToGroup(String groupId, AddGroupResourceRequest request) {
		try {
			AccessGroup group = groupRepository.findById(groupId)
					.orElseThrow(() -> new NotFoundException("Group not found with id: " + groupId));
			AccessResource resource = resourceRepository.findById(request.getResourceId())
					.orElseThrow(() -> new NotFoundException("Resource not found with id: " + request.getResourceId()));

			Optional<GroupAccessAssignment> existingAssignment = groupAccessAssignmentRepository
					.findByGroupIdAndResourceId(groupId, request.getResourceId());
			if (existingAssignment.isPresent()) {
				throw new IllegalStateException("Resource already associated with the group.");
			}

			GroupAccessAssignment assignment = new GroupAccessAssignment();
			assignment.setGroup(group);
			assignment.setResource(resource);

			if (request.getAccessLevelId() != null && !request.getAccessLevelId().isEmpty()) {
				AccessRole role = roleRepository.findById(request.getAccessLevelId()).orElseThrow(
						() -> new NotFoundException("Role not found with id: " + request.getAccessLevelId()));
				assignment.setAccessRole(role);
			}

			groupAccessAssignmentRepository.save(assignment);

			return true;
		} catch (NotFoundException e) {
			logger.error("Error adding resource to group. Group ID: {}, Resource ID: {}", groupId,
					request.getResourceId(), e);
			throw e;
		}
	}

	public boolean removeResourceFromGroup(String groupId, String resourceId, String roleId) {
		try {
			groupRepository.findById(groupId)
					.orElseThrow(() -> new NotFoundException("Group not found with id: " + groupId));
			resourceRepository.findById(resourceId)
					.orElseThrow(() -> new NotFoundException("Resource not found with id: " + resourceId));
			if (roleId != null && !roleId.isEmpty()) {
				roleRepository.findById(roleId)
						.orElseThrow(() -> new NotFoundException("Role not found with id: " + roleId));
			}

			Optional<GroupAccessAssignment> assignmentOpt = groupAccessAssignmentRepository
					.findByGroupIdAndResourceIdAndRoleId(groupId, resourceId, roleId);

			if (assignmentOpt.isPresent()) {
				GroupAccessAssignment assignment = assignmentOpt.get();
				groupAccessAssignmentRepository.delete(assignment);
				return true;
			} else {
				throw new NotFoundException("No assignment found for provided group, resource and role combination");
			}

		} catch (NotFoundException e) {
			logger.error("Error removing resource from group. Group ID: {}, Resource ID: {}", groupId, resourceId, e);
			throw e;
		}
	}

}
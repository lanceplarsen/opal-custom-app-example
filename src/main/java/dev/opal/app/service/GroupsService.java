package dev.opal.app.service;

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
import dev.opal.app.entity.AccessUser;
import dev.opal.app.exceptions.NotFoundException;
import dev.opal.app.mapper.GroupsMapper;
import dev.opal.app.repository.GroupRepository;
import dev.opal.app.repository.ResourceRepository;
import dev.opal.app.repository.UserRepository;

@Service
public class GroupsService {

	private final GroupRepository groupRepository;
	private final UserRepository userRepository;
	private final ResourceRepository resourceRepository;
	private final Logger logger = LoggerFactory.getLogger(GroupsService.class);

	public GroupsService(GroupRepository groupRepository, UserRepository userRepository,
			ResourceRepository resourceRepository) {
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
		this.resourceRepository = resourceRepository;
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
			return GroupsMapper.toGroupResourcesResponse(group.getResources());
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

	public boolean addResourceToGroup(String group_id, AddGroupResourceRequest request) {
		try {
			AccessGroup group = groupRepository.findById(group_id)
					.orElseThrow(() -> new NotFoundException("Group not found with id: " + group_id));
			AccessResource resource = resourceRepository.findById(request.getResourceId())
					.orElseThrow(() -> new NotFoundException("Resource not found with id: " + request.getResourceId()));
			group.addResource(resource);
			groupRepository.save(group);
			return true;
		} catch (NotFoundException e) {
			logger.error("Error adding resource to group. Group ID: {}, Resource ID: {}", group_id,
					request.getResourceId(), e);
			throw e;
		}
	}
}
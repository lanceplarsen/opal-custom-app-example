package dev.opal.app.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.opal.app.entity.Group;
import dev.opal.app.entity.Resource;
import dev.opal.app.entity.User;
import dev.opal.app.mapper.GroupsMapper;
import dev.opal.app.model.AddGroupResourceRequest;
import dev.opal.app.model.AddGroupUserRequest;
import dev.opal.app.model.GroupResourcesResponse;
import dev.opal.app.model.GroupResponse;
import dev.opal.app.model.GroupUsersResponse;
import dev.opal.app.model.GroupsResponse;
import dev.opal.app.repository.GroupRepository;
import dev.opal.app.repository.ResourceRepository;
import dev.opal.app.repository.UserRepository;

@Service
public class GroupsService {

	private final GroupRepository groupRepository;
	private final UserRepository userRepository;
	private final ResourceRepository resourceRepository;

	public GroupsService(GroupRepository groupRepository, UserRepository userRepository,
			ResourceRepository resourceRepository) {
		this.groupRepository = groupRepository;
		this.userRepository = userRepository;
		this.resourceRepository = resourceRepository;
	}

	public GroupsResponse getAllGroups() {
		return GroupsMapper.toGroupsResponse(groupRepository.findAll());
	}

	public void createGroup(String name, String description) {
		Group group = new Group();
		group.setName(name);
		group.setDescription(description);
		groupRepository.saveAndFlush(group);
	}

	public Optional<GroupResponse> getGroupById(String group_id) {
		return groupRepository.findById(group_id).map(GroupsMapper::toGroupResponse);
	}

	public Optional<GroupUsersResponse> getUsersForGroup(String group_id) {
		return groupRepository.findById(group_id).map(group -> GroupsMapper.toGroupUsersResponse(group.getUsers()));
	}

	public Optional<GroupResourcesResponse> getResourcesForGroup(String group_id) {
		return groupRepository.findById(group_id)
				.map(group -> GroupsMapper.toGroupResourcesResponse(group.getResources()));
	}

	public boolean addUserToGroup(String group_id, AddGroupUserRequest request) {
		Optional<Group> groupOptional = groupRepository.findById(group_id);
		Optional<User> userOptional = userRepository.findById(request.getUserId());

		if (groupOptional.isPresent() && userOptional.isPresent()) {
			Group group = groupOptional.get();
			User user = userOptional.get();
			group.getUsers().add(user);
			groupRepository.save(group);
			return true;
		}
		return false;
	}

	public boolean addResourceToGroup(String group_id, AddGroupResourceRequest request) {
		Optional<Group> groupOptional = groupRepository.findById(group_id);
		Optional<Resource> resourceOptional = resourceRepository.findById(request.getResourceId());

		if (groupOptional.isPresent() && resourceOptional.isPresent()) {
			Group group = groupOptional.get();
			Resource resource = resourceOptional.get();
			group.addResource(resource);
			groupRepository.save(group);
			return true;
		}
		return false;
	}
}
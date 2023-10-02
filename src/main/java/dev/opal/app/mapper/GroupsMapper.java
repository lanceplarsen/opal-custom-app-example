package dev.opal.app.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dev.opal.app.dto.GroupDetailDTO;
import dev.opal.app.dto.GroupResourceDTO;
import dev.opal.app.dto.GroupResponseDTO;
import dev.opal.app.dto.GroupUserDTO;
import dev.opal.app.dto.GroupUsersResponseDTO;
import dev.opal.app.dto.GroupsResponseDTO;
import dev.opal.app.entity.Group;
import dev.opal.app.entity.Resource;
import dev.opal.app.entity.User;
import dev.opal.app.model.GroupDetail;
import dev.opal.app.model.GroupResource;
import dev.opal.app.model.GroupResourcesResponse;
import dev.opal.app.model.GroupResponse;
import dev.opal.app.model.GroupUser;
import dev.opal.app.model.GroupUsersResponse;
import dev.opal.app.model.GroupsResponse;

public class GroupsMapper {

	// Direct Conversions

	public static GroupsResponse toGroupsResponse(List<Group> groups) {
		GroupsResponseDTO dtoResponse = toGroupsResponseDTO(groups);
		List<Group> domainGroups = dtoResponse.getGroups().stream().map(GroupsMapper::toGroupFromDTO)
				.collect(Collectors.toList());
		return new GroupsResponse(domainGroups);
	}

	public static GroupResponse toGroupResponse(Group group) {
		GroupResponseDTO dtoResponse = toGroupResponseDTO(group);
		GroupDetail detail = new GroupDetail(dtoResponse.getGroup().getId(), dtoResponse.getGroup().getName(),
				dtoResponse.getGroup().getDescription());
		return new GroupResponse(detail);
	}

	public static GroupUsersResponse toGroupUsersResponse(Set<User> users) {
		GroupUsersResponseDTO dtoResponse = toGroupUsersResponseDTO(users);
		List<GroupUser> groupUsers = dtoResponse.getUsers().stream()
				.map(userDTO -> new GroupUser(userDTO.getUserId(), userDTO.getEmail())).collect(Collectors.toList());
		return new GroupUsersResponse(groupUsers);
	}

	// Group and GroupDTO Conversions

	public static GroupDetailDTO toGroupDTO(Group group) {
		return new GroupDetailDTO(group.getId(), group.getName(), group.getDescription());
	}

	public static GroupsResponseDTO toGroupsResponseDTO(List<Group> groups) {
		List<GroupDetailDTO> groupDTOs = groups.stream().map(GroupsMapper::toGroupDTO).collect(Collectors.toList());
		return new GroupsResponseDTO(groupDTOs);
	}

	public static Group toGroupFromDTO(GroupDetailDTO dto) {
		Group group = new Group();
		group.setId(dto.getId());
		group.setName(dto.getName());
		group.setDescription(dto.getDescription());
		return group;
	}

	// GroupResponse and GroupResponseDTO Conversions

	public static GroupResponseDTO toGroupResponseDTO(Group group) {
		GroupDetailDTO groupDTO = toGroupDTO(group);
		return new GroupResponseDTO(groupDTO);
	}

	// GroupUsersResponse, GroupUsersResponseDTO and GroupUser Conversions

	public static GroupUserDTO toUserDTO(User user) {
		return new GroupUserDTO(user.getId(), user.getEmail());
	}

	public static GroupUsersResponseDTO toGroupUsersResponseDTO(Set<User> users) {
		List<GroupUserDTO> userDTOs = users.stream().map(GroupsMapper::toUserDTO).collect(Collectors.toList());
		return new GroupUsersResponseDTO(userDTOs);
	}

	// GroupResource, GroupResourceDTO and GroupResourcesResponse Conversions

	public static GroupResourceDTO toResourceDTO(Resource resource) {
		return new GroupResourceDTO(resource.getId(), resource.getName());
	}

	public static GroupResourcesResponse toGroupResourcesResponse(Set<Resource> resources) {
		List<GroupResource> groupResources = resources.stream().map(GroupsMapper::toResourceDTO)
				.map(dto -> new GroupResource(dto.getResourceId(), null)).collect(Collectors.toList());
		return new GroupResourcesResponse(groupResources);
	}
}
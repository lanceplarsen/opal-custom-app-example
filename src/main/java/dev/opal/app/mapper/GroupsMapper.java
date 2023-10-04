package dev.opal.app.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dev.opal.app.codegen.model.Group;
import dev.opal.app.codegen.model.GroupResource;
import dev.opal.app.codegen.model.GroupResourcesResponse;
import dev.opal.app.codegen.model.GroupResponse;
import dev.opal.app.codegen.model.GroupUser;
import dev.opal.app.codegen.model.GroupUsersResponse;
import dev.opal.app.codegen.model.GroupsResponse;
import dev.opal.app.dto.GroupDetailDTO;
import dev.opal.app.dto.GroupResourceDTO;
import dev.opal.app.dto.GroupResponseDTO;
import dev.opal.app.dto.GroupUserDTO;
import dev.opal.app.dto.GroupUsersResponseDTO;
import dev.opal.app.dto.GroupsResponseDTO;
import dev.opal.app.entity.AccessGroup;
import dev.opal.app.entity.AccessResource;
import dev.opal.app.entity.AccessUser;

public class GroupsMapper {

	// Direct Conversions

	public static GroupsResponse toGroupsResponse(List<AccessGroup> groups) {
		GroupsResponseDTO dtoResponse = toGroupsResponseDTO(groups);
		List<Group> domainGroups = dtoResponse.getGroups().stream().map(GroupsMapper::toGroupFromDTO)
				.collect(Collectors.toList());
		return new GroupsResponse(domainGroups);
	}

	public static GroupResponse toGroupResponse(AccessGroup group) {
		GroupResponseDTO dtoResponse = toGroupResponseDTO(group);
		Group detail = new Group(dtoResponse.getGroup().getId(), dtoResponse.getGroup().getName(),
				dtoResponse.getGroup().getDescription());
		return new GroupResponse(detail);
	}

	public static GroupUsersResponse toGroupUsersResponse(Set<AccessUser> users) {
		GroupUsersResponseDTO dtoResponse = toGroupUsersResponseDTO(users);
		List<GroupUser> groupUsers = dtoResponse.getUsers().stream()
				.map(userDTO -> new GroupUser(userDTO.getUserId(), userDTO.getEmail())).collect(Collectors.toList());
		return new GroupUsersResponse(groupUsers);
	}

	// Group and GroupDTO Conversions

	public static GroupDetailDTO toGroupDTO(AccessGroup group) {
		return new GroupDetailDTO(group.getId(), group.getName(), group.getDescription());
	}

	public static GroupsResponseDTO toGroupsResponseDTO(List<AccessGroup> groups) {
		List<GroupDetailDTO> groupDTOs = groups.stream().map(GroupsMapper::toGroupDTO).collect(Collectors.toList());
		return new GroupsResponseDTO(groupDTOs);
	}

	public static Group toGroupFromDTO(GroupDetailDTO dto) {
		return new Group(dto.getId(), dto.getName(), dto.getDescription());
	}

	// GroupResponse and GroupResponseDTO Conversions

	public static GroupResponseDTO toGroupResponseDTO(AccessGroup group) {
		GroupDetailDTO groupDTO = toGroupDTO(group);
		return new GroupResponseDTO(groupDTO);
	}

	// GroupUsersResponse, GroupUsersResponseDTO and GroupUser Conversions

	public static GroupUserDTO toUserDTO(AccessUser user) {
		return new GroupUserDTO(user.getId(), user.getEmail());
	}

	public static GroupUsersResponseDTO toGroupUsersResponseDTO(Set<AccessUser> users) {
		List<GroupUserDTO> userDTOs = users.stream().map(GroupsMapper::toUserDTO).collect(Collectors.toList());
		return new GroupUsersResponseDTO(userDTOs);
	}

	// GroupResource, GroupResourceDTO and GroupResourcesResponse Conversions

	public static GroupResourceDTO toResourceDTO(AccessResource resource) {
		return new GroupResourceDTO(resource.getId(), resource.getName());
	}

	public static GroupResourcesResponse toGroupResourcesResponse(Set<AccessResource> resources) {
		List<GroupResource> groupResources = resources.stream().map(GroupsMapper::toResourceDTO)
				.map(dto -> new GroupResource(dto.getResourceId(), null)).collect(Collectors.toList());
		return new GroupResourcesResponse(groupResources);
	}
}
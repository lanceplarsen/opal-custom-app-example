package dev.opal.app.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import dev.opal.app.codegen.model.AccessLevel;
import dev.opal.app.codegen.model.Group;
import dev.opal.app.codegen.model.GroupResource;
import dev.opal.app.codegen.model.GroupResourcesResponse;
import dev.opal.app.codegen.model.GroupResponse;
import dev.opal.app.codegen.model.GroupUser;
import dev.opal.app.codegen.model.GroupUsersResponse;
import dev.opal.app.codegen.model.GroupsResponse;
import dev.opal.app.dto.GroupDTO;
import dev.opal.app.dto.GroupResourceDTO;
import dev.opal.app.dto.GroupUserDTO;
import dev.opal.app.entity.AccessGroup;
import dev.opal.app.entity.AccessUser;
import dev.opal.app.entity.GroupAccessAssignment;

public class GroupsMapper {

	// Direct Conversions

	public static GroupsResponse toGroupsResponse(List<AccessGroup> groups) {
		List<GroupDTO> groupDTOs = toGroupDTOs(groups);
		List<Group> domainGroups = groupDTOs.stream().map(GroupsMapper::toGroupFromDTO).collect(Collectors.toList());
		return new GroupsResponse(domainGroups);
	}

	public static GroupResponse toGroupResponse(AccessGroup group) {
		GroupDTO dto = toGroupDTO(group);
		return new GroupResponse(toGroupFromDTO(dto));
	}

	public static GroupUsersResponse toGroupUsersResponse(Set<AccessUser> users) {
		List<GroupUserDTO> userDTOs = toUserDTOs(users);
		List<GroupUser> domainUsers = userDTOs.stream().map(GroupsMapper::toGroupUserFromDTO)
				.collect(Collectors.toList());
		return new GroupUsersResponse(domainUsers);
	}

	public static GroupResourcesResponse toGroupResourcesResponse(Set<GroupAccessAssignment> resources) {
		List<GroupResourceDTO> resourcesGroupDTOs = toGroupResourceDTOs(resources);
		List<GroupResource> domainResourceGroups = resourcesGroupDTOs.stream().map(GroupsMapper::toGroupResourceFromDTO)
				.collect(Collectors.toList());
		return new GroupResourcesResponse(domainResourceGroups);
	}

	// Helper methods

	public static GroupDTO toGroupDTO(AccessGroup group) {
		return new GroupDTO(group.getId(), group.getName(), group.getDescription());
	}

	public static List<GroupDTO> toGroupDTOs(List<AccessGroup> groups) {
		return groups.stream().map(GroupsMapper::toGroupDTO).collect(Collectors.toList());
	}

	public static Group toGroupFromDTO(GroupDTO dto) {
		return new Group(dto.getId(), dto.getName(), dto.getDescription());
	}

	public static List<GroupUserDTO> toUserDTOs(Set<AccessUser> users) {
		return users.stream().map(GroupsMapper::toUserDTO).collect(Collectors.toList());
	}

	public static GroupUserDTO toUserDTO(AccessUser user) {
		return new GroupUserDTO(user.getId(), user.getEmail());
	}

	public static GroupUser toGroupUserFromDTO(GroupUserDTO dto) {
		return new GroupUser(dto.getUserId(), dto.getEmail());
	}

	public static GroupResourceDTO toGroupResourceDTO(GroupAccessAssignment resource) {
		AccessLevel accessLevel = null;

		if (resource.getAccessRole() != null) {
			accessLevel = new AccessLevel();
			accessLevel.setId(resource.getAccessRole().getId());
			accessLevel.setName(resource.getAccessRole().getName());
		}

		return new GroupResourceDTO(resource.getResource().getId(), accessLevel);
	}

	public static GroupResource toGroupResourceFromDTO(GroupResourceDTO resource) {
		return new GroupResource(resource.getResourceId(), resource.getAccessLevel());
	}

	public static List<GroupResourceDTO> toGroupResourceDTOs(Set<GroupAccessAssignment> resources) {
		return resources.stream().map(GroupsMapper::toGroupResourceDTO).collect(Collectors.toList());
	}

}

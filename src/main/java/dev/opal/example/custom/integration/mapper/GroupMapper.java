package dev.opal.example.custom.integration.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.opal.example.custom.integration.codegen.connector.model.Group;
import dev.opal.example.custom.integration.codegen.connector.model.GroupResource;
import dev.opal.example.custom.integration.codegen.connector.model.GroupResourcesResponse;
import dev.opal.example.custom.integration.codegen.connector.model.GroupResponse;
import dev.opal.example.custom.integration.codegen.connector.model.GroupUser;
import dev.opal.example.custom.integration.codegen.connector.model.GroupUsersResponse;
import dev.opal.example.custom.integration.codegen.connector.model.GroupsResponse;
import dev.opal.example.custom.integration.dto.GroupDTO;
import dev.opal.example.custom.integration.dto.GroupResourceDTO;
import dev.opal.example.custom.integration.dto.GroupUserDTO;
import dev.opal.example.custom.integration.entity.AccessGroup;
import dev.opal.example.custom.integration.entity.AccessUser;
import dev.opal.example.custom.integration.entity.GroupAccessAssignment;

@Mapper(componentModel = "spring")
public interface GroupMapper {

	// Direct Conversions

	default GroupsResponse toGroupsResponse(List<AccessGroup> groups) {
		return new GroupsResponse(toGroups(groups));
	}

	default GroupResponse toGroupResponse(AccessGroup group) {
		return new GroupResponse(toGroup(group));
	}

	default GroupUsersResponse toGroupUsersResponse(Set<AccessUser> users) {
		return new GroupUsersResponse(toGroupUsers(users));
	}

	default GroupResourcesResponse toGroupResourcesResponse(Set<GroupAccessAssignment> resources) {
		return new GroupResourcesResponse(toGroupResources(resources));
	}

	// Mappings for individual entities

	GroupDTO toGroupDTO(AccessGroup group);

	Group toGroup(AccessGroup group);

	Group toGroupFromDTO(GroupDTO dto);

	@Mapping(source = "id", target = "userId")
	GroupUserDTO toGroupUserDTO(AccessUser user);

	@Mapping(source = "id", target = "userId")
	GroupUser toGroupUser(AccessUser user);

	GroupUser toGroupUserFromDTO(GroupUserDTO dto);

	@Mapping(source = "resource.id", target = "resourceId")
	@Mapping(source = "accessRole", target = "accessLevel")
	GroupResourceDTO toGroupResourceDTO(GroupAccessAssignment resource);

	@Mapping(source = "resource.id", target = "resourceId")
	@Mapping(source = "accessRole", target = "accessLevel")
	GroupResource toGroupResource(GroupAccessAssignment resource);

	GroupResource toGroupResourceFromDTO(GroupResourceDTO dto);

	// Mappings for collections

	List<Group> toGroups(List<AccessGroup> groups);

	List<GroupUser> toGroupUsers(Set<AccessUser> users);

	List<GroupResource> toGroupResources(Set<GroupAccessAssignment> resources);
}
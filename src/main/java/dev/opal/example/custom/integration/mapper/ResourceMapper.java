package dev.opal.example.custom.integration.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.opal.example.custom.integration.codegen.connector.model.AccessLevel;
import dev.opal.example.custom.integration.codegen.connector.model.Resource;
import dev.opal.example.custom.integration.codegen.connector.model.ResourceAccessLevelsResponse;
import dev.opal.example.custom.integration.codegen.connector.model.ResourceResponse;
import dev.opal.example.custom.integration.codegen.connector.model.ResourceUser;
import dev.opal.example.custom.integration.codegen.connector.model.ResourceUsersResponse;
import dev.opal.example.custom.integration.codegen.connector.model.ResourcesResponse;
import dev.opal.example.custom.integration.dto.AccessLevelDTO;
import dev.opal.example.custom.integration.dto.ResourceDTO;
import dev.opal.example.custom.integration.dto.ResourceUserDTO;
import dev.opal.example.custom.integration.entity.AccessResource;
import dev.opal.example.custom.integration.entity.AccessRole;
import dev.opal.example.custom.integration.entity.ResourceAccessAssignment;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

	// Direct Conversions

	default ResourcesResponse toResourcesResponse(Collection<AccessResource> resources) {
		return new ResourcesResponse(toResources(resources));
	}

	default ResourceResponse toResourceResponse(AccessResource resource) {
		return new ResourceResponse(toResource(resource));
	}

	default ResourceUsersResponse toResourceUsersResponse(Set<ResourceAccessAssignment> resourceAssignments) {
		return new ResourceUsersResponse(toResourceUsers(resourceAssignments));
	}

	default ResourceAccessLevelsResponse toResourceAccessLevelsResponse(Set<AccessRole> roles) {
		return new ResourceAccessLevelsResponse(toAccessLevels(roles));
	}

	// Mappings for individual entities

	ResourceDTO toResourceDTO(AccessResource resource);

	Resource toResource(AccessResource resource);

	Resource toResourceFromDTO(ResourceDTO dto);

	AccessLevelDTO toAccessLevelDTO(AccessRole role);

	AccessLevel toAccessLevel(AccessRole role);

	AccessLevel toAccessLevelFromDTO(AccessLevelDTO dto);

	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.email", target = "email")
	@Mapping(source = "accessRole", target = "accessLevel")
	ResourceUserDTO toUserDTO(ResourceAccessAssignment resourceAssignment);

	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.email", target = "email")
	@Mapping(source = "accessRole", target = "accessLevel")
	ResourceUser toResourceUser(ResourceAccessAssignment resourceAssignment);

	ResourceUser toResourceUserFromDTO(ResourceUserDTO dto);

	// Mappings for collections

	List<Resource> toResources(Collection<AccessResource> resources);

	List<ResourceUser> toResourceUsers(Set<ResourceAccessAssignment> resourceAssignments);

	List<AccessLevel> toAccessLevels(Set<AccessRole> roles);
}
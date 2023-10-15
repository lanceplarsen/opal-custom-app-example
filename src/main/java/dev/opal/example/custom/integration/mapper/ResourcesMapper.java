package dev.opal.example.custom.integration.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

public class ResourcesMapper {

	// Direct Conversions

	public static ResourceUsersResponse toResourceUsersResponse(Set<ResourceAccessAssignment> resources) {
		List<ResourceUserDTO> resourceUserDTOs = toResourceUserDTOs(resources);
		List<ResourceUser> domainResourceUsers = resourceUserDTOs.stream().map(ResourcesMapper::toResourceUser)
				.collect(Collectors.toList());
		return new ResourceUsersResponse(domainResourceUsers);
	}

	public static ResourcesResponse toResourcesResponse(Collection<AccessResource> resources) {
		List<ResourceDTO> resourceDTOs = toResourceDTOs(resources);
		List<Resource> domainResources = resourceDTOs.stream().map(ResourcesMapper::toResourceDomain)
				.collect(Collectors.toList());
		return new ResourcesResponse(domainResources);
	}

	public static ResourceResponse toResourceResponse(AccessResource resource) {
		ResourceDTO resourceDTO = toResourceDTO(resource);
		return new ResourceResponse(toResourceDomain(resourceDTO));
	}

	public static ResourceAccessLevelsResponse toResourceAccessLevelsResponse(Set<AccessRole> roles) {
		List<AccessLevelDTO> accessLevelDTOs = toResourceAccessLevelDTOs(roles);
		List<AccessLevel> domainResources = accessLevelDTOs.stream().map(ResourcesMapper::toAccessLevelDomain)
				.collect(Collectors.toList());
		return new ResourceAccessLevelsResponse(domainResources);
	}

	// Helper methods

	public static List<ResourceUserDTO> toResourceUserDTOs(Set<ResourceAccessAssignment> resources) {
		return resources.stream().map(ResourcesMapper::toResourceUserDTO).collect(Collectors.toList());
	}

	public static List<ResourceDTO> toResourceDTOs(Collection<AccessResource> resources) {
		return resources.stream().map(ResourcesMapper::toResourceDTO).collect(Collectors.toList());
	}

	public static ResourceDTO toResourceDTO(AccessResource resource) {
		return new ResourceDTO(resource.getId(), resource.getName(), resource.getDescription());
	}

	public static AccessLevelDTO toAccessLevelDTO(AccessRole role) {
		return new AccessLevelDTO(role.getId(), role.getName());
	}

	public static Resource toResourceDomain(ResourceDTO dto) {
		return new Resource(dto.getId(), dto.getName(), dto.getDescription());
	}

	public static AccessLevel toAccessLevelDomain(AccessLevelDTO dto) {
		return new AccessLevel(dto.getId(), dto.getName());
	}

	public static ResourceUserDTO toResourceUserDTO(ResourceAccessAssignment resource) {
		AccessLevel accessLevel = null;

		if (resource.getAccessRole() != null) {
			accessLevel = new AccessLevel();
			accessLevel.setId(resource.getAccessRole().getId());
			accessLevel.setName(resource.getAccessRole().getName());
		}
		return new ResourceUserDTO(resource.getUser().getId(), resource.getUser().getEmail(), accessLevel);
	}

	public static ResourceUser toResourceUser(ResourceUserDTO dto) {
		ResourceUser resourceUser = new ResourceUser();
		resourceUser.setUserId(dto.getUserId());
		resourceUser.setEmail(dto.getEmail());
		return resourceUser;
	}

	public static List<AccessLevelDTO> toResourceAccessLevelDTOs(Set<AccessRole> roles) {
		return roles.stream().map(ResourcesMapper::toAccessLevelDTO).collect(Collectors.toList());
	}
}
package dev.opal.app.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import dev.opal.app.codegen.model.ResourceResponse;
import dev.opal.app.codegen.model.ResourceUser;
import dev.opal.app.codegen.model.Resource;
import dev.opal.app.codegen.model.ResourceUsersResponse;
import dev.opal.app.codegen.model.ResourcesResponse;
import dev.opal.app.dto.ResourceDTO;
import dev.opal.app.dto.ResourceResponseDTO;
import dev.opal.app.dto.ResourceUserDTO;
import dev.opal.app.dto.ResourceUsersResponseDTO;
import dev.opal.app.dto.ResourcesResponseDTO;
import dev.opal.app.entity.AccessResource;
import dev.opal.app.entity.AccessUser;

public class ResourcesMapper {

	// Direct Conversions

	public static ResourceUsersResponse toResourceUsersResponse(Collection<AccessUser> users) {
		ResourceUsersResponseDTO dtoResponse = toResourceUsersResponseDTO(users);
		return toResourceUsersResponse(dtoResponse);
	}

	public static ResourcesResponse toResourcesResponse(List<AccessResource> resources) {
		ResourcesResponseDTO dtoResponse = toResourcesResponseDTO(resources);
		return toResourcesResponse(dtoResponse);
	}

	public static ResourceResponse toResourceResponse(AccessResource resource) {
		ResourceResponseDTO dtoResponse = toResourceResponseDTO(resource);
		return toResourceResponse(dtoResponse);
	}

	// Resource Conversions

	public static ResourceResponseDTO toResourceResponseDTO(AccessResource resource) {
		return new ResourceResponseDTO(toResourceDTO(resource));
	}

	public static ResourceResponse toResourceResponse(ResourceResponseDTO dto) {
		return new ResourceResponse(toResourceDomain(dto.getResource()));
	}

	public static ResourceDTO toResourceDTO(AccessResource resource) {
		return new ResourceDTO(resource.getId(), resource.getName(), resource.getDescription());
	}

	public static Resource toResourceDomain(ResourceDTO dto) {
		return new Resource(dto.getId(), dto.getName(), dto.getDescription());
	}

	public static ResourcesResponseDTO toResourcesResponseDTO(List<AccessResource> resources) {
		List<ResourceDTO> resourceDTOs = resources.stream().map(ResourcesMapper::toResourceDTO)
				.collect(Collectors.toList());
		return new ResourcesResponseDTO(resourceDTOs);
	}

	public static ResourcesResponse toResourcesResponse(ResourcesResponseDTO dto) {
		List<Resource> resources = dto.getResources().stream().map(ResourcesMapper::toResourceDomain)
				.collect(Collectors.toList());
		return new ResourcesResponse(resources);
	}

	// User Conversions

	public static ResourceUserDTO toResourceUserDTO(AccessUser user) {
		return new ResourceUserDTO(user.getId(), user.getEmail());
	}

	public static ResourceUsersResponseDTO toResourceUsersResponseDTO(Collection<AccessUser> users) {
		List<ResourceUserDTO> userDTOs = users.stream().map(ResourcesMapper::toResourceUserDTO)
				.collect(Collectors.toList());
		return new ResourceUsersResponseDTO(userDTOs);
	}

	public static ResourceUsersResponse toResourceUsersResponse(ResourceUsersResponseDTO dto) {
		List<ResourceUser> resourceUsers = dto.getUsers().stream().map(ResourcesMapper::toResourceUser)
				.collect(Collectors.toList());
		return new ResourceUsersResponse(resourceUsers);
	}

	public static ResourceUser toResourceUser(ResourceUserDTO dto) {
		ResourceUser resourceUser = new ResourceUser();
		resourceUser.setUserId(dto.getUserId());
		resourceUser.setEmail(dto.getEmail());
		return resourceUser;
	}
}
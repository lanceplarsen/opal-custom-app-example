package dev.opal.app.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import dev.opal.app.dto.ResourceDTO;
import dev.opal.app.dto.ResourceResponseDTO;
import dev.opal.app.dto.ResourceUserDTO;
import dev.opal.app.dto.ResourceUsersResponseDTO;
import dev.opal.app.dto.ResourcesResponseDTO;
import dev.opal.app.entity.Resource;
import dev.opal.app.entity.User;
import dev.opal.app.model.ResourceResponse;
import dev.opal.app.model.ResourceUser;
import dev.opal.app.model.ResourceUsersResponse;
import dev.opal.app.model.ResourcesResponse;

public class ResourcesMapper {
	
	// Direct Conversions
	
	public static ResourceUsersResponse toResourceUsersResponse(Collection<User> users) {
	    ResourceUsersResponseDTO dtoResponse = toResourceUsersResponseDTO(users);
	    return toResourceUsersResponse(dtoResponse);
	}

	public static ResourcesResponse toResourcesResponse(List<Resource> resources) {
	    ResourcesResponseDTO dtoResponse = toResourcesResponseDTO(resources);
	    return toResourcesResponse(dtoResponse);
	}

	public static ResourceResponse toResourceResponse(Resource resource) {
	    ResourceResponseDTO dtoResponse = toResourceResponseDTO(resource);
	    return toResourceResponse(dtoResponse);
	}


	// Resource Conversions

	public static ResourceResponseDTO toResourceResponseDTO(Resource resource) {
		return new ResourceResponseDTO(toResourceDTO(resource));
	}

	public static ResourceResponse toResourceResponse(ResourceResponseDTO dto) {
		return new ResourceResponse(toResourceDomain(dto.getResource()));
	}

	public static ResourceDTO toResourceDTO(Resource resource) {
		return new ResourceDTO(resource.getId(), resource.getName(), resource.getDescription());
	}

	public static Resource toResourceDomain(ResourceDTO dto) {
		Resource resource = new Resource();
		resource.setId(dto.getId());
		resource.setName(dto.getName());
		resource.setDescription(dto.getDescription());
		return resource;
	}

	public static ResourcesResponseDTO toResourcesResponseDTO(List<Resource> resources) {
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

	public static ResourceUserDTO toResourceUserDTO(User user) {
		return new ResourceUserDTO(user.getId(), user.getEmail());
	}

	public static ResourceUsersResponseDTO toResourceUsersResponseDTO(Collection<User> users) {
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
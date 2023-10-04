package dev.opal.app.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import dev.opal.app.codegen.model.Resource;
import dev.opal.app.codegen.model.ResourceResponse;
import dev.opal.app.codegen.model.ResourceUser;
import dev.opal.app.codegen.model.ResourceUsersResponse;
import dev.opal.app.codegen.model.ResourcesResponse;
import dev.opal.app.dto.ResourceDTO;
import dev.opal.app.dto.ResourceUserDTO;
import dev.opal.app.entity.AccessResource;
import dev.opal.app.entity.AccessUser;

public class ResourcesMapper {

    // Direct Conversions

    public static ResourceUsersResponse toResourceUsersResponse(Collection<AccessUser> users) {
        List<ResourceUserDTO> resourceUserDTOs = toResourceUserDTOs(users);
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

    // Helper methods

    public static List<ResourceUserDTO> toResourceUserDTOs(Collection<AccessUser> users) {
        return users.stream().map(ResourcesMapper::toResourceUserDTO).collect(Collectors.toList());
    }

    public static List<ResourceDTO> toResourceDTOs(Collection<AccessResource> resources) {
        return resources.stream().map(ResourcesMapper::toResourceDTO).collect(Collectors.toList());
    }

    public static ResourceDTO toResourceDTO(AccessResource resource) {
        return new ResourceDTO(resource.getId(), resource.getName(), resource.getDescription());
    }

    public static Resource toResourceDomain(ResourceDTO dto) {
        return new Resource(dto.getId(), dto.getName(), dto.getDescription());
    }

    public static ResourceUserDTO toResourceUserDTO(AccessUser user) {
        return new ResourceUserDTO(user.getId(), user.getEmail());
    }

    public static ResourceUser toResourceUser(ResourceUserDTO dto) {
        ResourceUser resourceUser = new ResourceUser();
        resourceUser.setUserId(dto.getUserId());
        resourceUser.setEmail(dto.getEmail());
        return resourceUser;
    }
}
package dev.opal.app.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dev.opal.app.codegen.model.AddResourceUserRequest;
import dev.opal.app.codegen.model.ResourceAccessLevelsResponse;
import dev.opal.app.codegen.model.ResourceResponse;
import dev.opal.app.codegen.model.ResourceUsersResponse;
import dev.opal.app.codegen.model.ResourcesResponse;
import dev.opal.app.entity.AccessResource;
import dev.opal.app.entity.AccessUser;
import dev.opal.app.mapper.ResourcesMapper;
import dev.opal.app.repository.ResourceRepository;
import dev.opal.app.repository.UserRepository;

@Service
public class ResourceService {

	private final ResourceRepository resourceRepository;
	private final UserRepository userRepository;

	public ResourceService(ResourceRepository resourceRepository, UserRepository userRepository) {
		this.resourceRepository = resourceRepository;
		this.userRepository = userRepository;
	}

	private final Logger logger = LoggerFactory.getLogger(ResourceService.class);

	public ResourcesResponse getAllResources() {
		return ResourcesMapper.toResourcesResponse(resourceRepository.findAll());
	}

	public Optional<ResourceResponse> getResourceById(String id) {
		return resourceRepository.findById(id).map(ResourcesMapper::toResourceResponse);
	}

	public Optional<ResourceUsersResponse> getResourceUsersById(String id) {
		Optional<AccessResource> optionalResource = resourceRepository.findById(id);
		if (optionalResource.isPresent()) {
			return Optional.of(ResourcesMapper.toResourceUsersResponse(optionalResource.get().getUsers()));
		}
		logger.warn("Resource with ID {} not found", id);
		return Optional.empty();
	}

	// TODO method
	public ResourceAccessLevelsResponse getAccessLevelsForResource(String resourceId) {
		ResourceAccessLevelsResponse response = new ResourceAccessLevelsResponse();
		return response;
	}

	public AccessResource createResource(String name, String description) {
		AccessResource resource = new AccessResource(name, description);
		return resourceRepository.saveAndFlush(resource);
	}

	public boolean addUserToResource(String resourceId, AddResourceUserRequest request) {
		Optional<AccessResource> optionalResource = resourceRepository.findById(resourceId);
		Optional<AccessUser> optionalUser = userRepository.findById(request.getUserId());

		if (optionalResource.isPresent() && optionalUser.isPresent()) {
			AccessResource resource = optionalResource.get();
			AccessUser user = optionalUser.get();
			resource.getUsers().add(user);
			resourceRepository.save(resource);
			return true;
		}
		return false;
	}
}
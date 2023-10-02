package dev.opal.app.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dev.opal.app.entity.Resource;
import dev.opal.app.entity.User;
import dev.opal.app.mapper.ResourcesMapper;
import dev.opal.app.model.AddResourceUserRequest;
import dev.opal.app.model.ResourceAccessLevelsResponse;
import dev.opal.app.model.ResourceResponse;
import dev.opal.app.model.ResourceUsersResponse;
import dev.opal.app.model.ResourcesResponse;
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
		Optional<Resource> optionalResource = resourceRepository.findById(id);
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

	public Resource createResource(String name, String description) {
		Resource resource = new Resource();
		resource.setName(name);
		resource.setDescription(description);
		return resourceRepository.saveAndFlush(resource);
	}

	public boolean addUserToResource(String resourceId, AddResourceUserRequest request) {
		Optional<Resource> optionalResource = resourceRepository.findById(resourceId);
		Optional<User> optionalUser = userRepository.findById(request.getUserId());

		if (optionalResource.isPresent() && optionalUser.isPresent()) {
			Resource resource = optionalResource.get();
			User user = optionalUser.get();
			resource.getUsers().add(user);
			resourceRepository.save(resource);
			return true;
		}
		return false;
	}
}
package dev.opal.app.service;

import java.util.Optional;
import java.util.Set;

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
import dev.opal.app.exceptions.NotFoundException;
import dev.opal.app.mapper.ResourcesMapper;
import dev.opal.app.repository.ResourceRepository;
import dev.opal.app.repository.UserRepository;

@Service
public class ResourceService {

	private final ResourceRepository resourceRepository;
	private final UserRepository userRepository;
	private final Logger logger = LoggerFactory.getLogger(ResourceService.class);

	public ResourceService(ResourceRepository resourceRepository, UserRepository userRepository) {
		this.resourceRepository = resourceRepository;
		this.userRepository = userRepository;
	}

	public ResourcesResponse getAllResources() {
		try {
			return ResourcesMapper.toResourcesResponse(resourceRepository.findAll());
		} catch (Exception e) {
			logger.error("Error fetching all resources", e);
			throw e;
		}
	}

	public Optional<ResourceResponse> getResourceById(String id) {
		try {
			return resourceRepository.findById(id).map(ResourcesMapper::toResourceResponse);
		} catch (Exception e) {
			logger.error("Error fetching resource by ID: {}", id, e);
			throw e;
		}
	}

	public Optional<ResourceUsersResponse> getResourceUsersById(String id) {
		try {
			AccessResource resource = resourceRepository.findById(id)
					.orElseThrow(() -> new NotFoundException("Resource not found with ID: " + id));
			return Optional.ofNullable(ResourcesMapper.toResourceUsersResponse(resource.getUsers()));
		} catch (NotFoundException e) {
			logger.error("Error fetching users for resource ID: {}", id, e);
			throw e;
		}
	}

	public ResourceAccessLevelsResponse getAccessLevelsForResource(String resourceId) {
		// Implement as required. For now, returning an empty response.
		ResourceAccessLevelsResponse response = new ResourceAccessLevelsResponse();
		return response;
	}

	public AccessResource createResource(String name, String description) {
		try {
			AccessResource resource = new AccessResource(name, description);
			return resourceRepository.saveAndFlush(resource);
		} catch (Exception e) {
			logger.error("Error creating resource with name: {}", name, e);
			throw e;
		}
	}

	public boolean addUserToResource(String resourceId, AddResourceUserRequest request) {
		try {
			AccessResource resource = resourceRepository.findById(resourceId)
					.orElseThrow(() -> new NotFoundException("Resource not found with ID: " + resourceId));
			AccessUser user = userRepository.findById(request.getUserId())
					.orElseThrow(() -> new NotFoundException("User not found with ID: " + request.getUserId()));
			resource.getUsers().add(user);
			resourceRepository.save(resource);
			return true;
		} catch (NotFoundException e) {
			logger.error("Error adding user to resource. Resource ID: {}, User ID: {}", resourceId, request.getUserId(),
					e);
			throw e;
		}
	}

	public boolean removeUserFromResource(String resourceId, String userId, String appId, String accessLevelId) {
		try {
			AccessResource resource = resourceRepository.findById(resourceId)
					.orElseThrow(() -> new NotFoundException("Resource not found with ID: " + resourceId));
			AccessUser user = userRepository.findById(userId)
					.orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
			Set<AccessUser> users = resource.getUsers();
			if (users.contains(user)) {
				users.remove(user);
				resourceRepository.save(resource);
				return true;
			} else {
				throw new NotFoundException("User not found in resource's access list");
			}
		} catch (NotFoundException e) {
			logger.error("Error removing user from resource. Resource ID: {}, User ID: {}", resourceId, userId, e);
			throw e;
		}
	}

}
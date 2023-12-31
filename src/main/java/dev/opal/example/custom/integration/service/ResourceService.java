package dev.opal.example.custom.integration.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dev.opal.example.custom.integration.codegen.connector.model.AddResourceUserRequest;
import dev.opal.example.custom.integration.codegen.connector.model.ResourceAccessLevelsResponse;
import dev.opal.example.custom.integration.codegen.connector.model.ResourceResponse;
import dev.opal.example.custom.integration.codegen.connector.model.ResourceUsersResponse;
import dev.opal.example.custom.integration.codegen.connector.model.ResourcesResponse;
import dev.opal.example.custom.integration.entity.AccessResource;
import dev.opal.example.custom.integration.entity.AccessRole;
import dev.opal.example.custom.integration.entity.AccessUser;
import dev.opal.example.custom.integration.entity.ResourceAccessAssignment;
import dev.opal.example.custom.integration.exceptions.NotFoundException;
import dev.opal.example.custom.integration.mapper.ResourceMapper;
import dev.opal.example.custom.integration.repository.ResourceAccessAssignmentRepository;
import dev.opal.example.custom.integration.repository.ResourceRepository;
import dev.opal.example.custom.integration.repository.RoleRepository;
import dev.opal.example.custom.integration.repository.UserRepository;

@Service
public class ResourceService {

	private final ResourceRepository resourceRepository;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final ResourceAccessAssignmentRepository resourceAccessAssignmentRepository;
	private final Logger logger = LoggerFactory.getLogger(ResourceService.class);
	private final ResourceMapper resourcesMapper;

	public ResourceService(ResourceRepository resourceRepository, UserRepository userRepository,
			RoleRepository roleRepository, ResourceAccessAssignmentRepository resourceAccessAssignmentRepository,
			ResourceMapper resourcesMapper) {
		this.resourceRepository = resourceRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.resourceAccessAssignmentRepository = resourceAccessAssignmentRepository;
		this.resourcesMapper = resourcesMapper;
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

	public AccessRole createRole(String resourceId, String roleName) {
		try {
			AccessResource resource = resourceRepository.findById(resourceId)
					.orElseThrow(() -> new NotFoundException("Resource not found with ID: " + resourceId));

			boolean roleExistsForResource = resource.getRoles().stream()
					.anyMatch(role -> roleName.equals(role.getName()));
			if (roleExistsForResource) {
				throw new IllegalArgumentException("Role with name " + roleName + " already exists for this resource.");
			}

			AccessRole accessRole = new AccessRole(roleName);
			accessRole = roleRepository.save(accessRole);

			resource.addRole(accessRole);
			resourceRepository.save(resource);

			return accessRole;
		} catch (Exception e) {
			logger.error("Error creating role with name: {} for resource ID: {}", roleName, resourceId, e);
			throw e;
		}
	}

	public ResourcesResponse getAllResources() {
		try {
			return resourcesMapper.toResourcesResponse(resourceRepository.findAll());
		} catch (Exception e) {
			logger.error("Error fetching all resources", e);
			throw e;
		}
	}

	public Optional<ResourceResponse> getResourceById(String id) {
		try {
			return resourceRepository.findById(id).map(resourcesMapper::toResourceResponse);
		} catch (Exception e) {
			logger.error("Error fetching resource by ID: {}", id, e);
			throw e;
		}
	}

	public Optional<ResourceUsersResponse> getResourceUsersById(String id) {
		try {
			AccessResource resource = resourceRepository.findById(id)
					.orElseThrow(() -> new NotFoundException("Resource not found with ID: " + id));
			return Optional.ofNullable(resourcesMapper.toResourceUsersResponse(resource.getUserAccessAssignments()));
		} catch (NotFoundException e) {
			logger.error("Error fetching users for resource ID: {}", id, e);
			throw e;
		}
	}

	public ResourceAccessLevelsResponse getAccessLevelsForResource(String id) {
		try {
			AccessResource resource = resourceRepository.findById(id)
					.orElseThrow(() -> new NotFoundException("Resource not found with ID: " + id));
			return resourcesMapper.toResourceAccessLevelsResponse(resource.getRoles());
		} catch (NotFoundException e) {
			logger.error("Error fetching access levels for resource ID: {}", id, e);
			throw e;
		}
	}

	public boolean addUserToResource(String resourceId, AddResourceUserRequest request) {
		try {
			AccessResource resource = resourceRepository.findById(resourceId)
					.orElseThrow(() -> new NotFoundException("Resource not found with ID: " + resourceId));

			AccessUser user = userRepository.findById(request.getUserId())
					.orElseThrow(() -> new NotFoundException("User not found with ID: " + request.getUserId()));

			AccessRole role = null;
			if (request.getAccessLevelId() != null) {
				role = roleRepository.findById(request.getAccessLevelId()).orElseThrow(
						() -> new NotFoundException("Access Level not found with ID: " + request.getAccessLevelId()));
			}

			ResourceAccessAssignment assignment = new ResourceAccessAssignment();
			assignment.setResource(resource);
			assignment.setUser(user);
			assignment.setAccessRole(role);

			resourceAccessAssignmentRepository.saveAndFlush(assignment);
			return true;

		} catch (NotFoundException e) {
			logger.error("Error adding user to resource. Resource ID: {}, User ID: {}", resourceId, request.getUserId(),
					e);
			throw e;
		}
	}

	public boolean removeUserFromResource(String resourceId, String userId, String appId, String accessLevelId) {
		try {
			ResourceAccessAssignment assignment = resourceAccessAssignmentRepository
					.findByResourceIdAndUserId(resourceId, userId).orElseThrow(() -> new NotFoundException(
							"No such assignment found for Resource ID: " + resourceId + " and User ID: " + userId));

			resourceAccessAssignmentRepository.delete(assignment);
			return true;

		} catch (NotFoundException e) {
			logger.error("Error removing user from resource. Resource ID: {}, User ID: {}", resourceId, userId, e);
			throw e;
		}
	}

}
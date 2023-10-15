package dev.opal.example.custom.integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.opal.example.custom.integration.entity.ResourceAccessAssignment;

@RepositoryRestResource
public interface ResourceAccessAssignmentRepository extends JpaRepository<ResourceAccessAssignment, Long> {
	Optional<ResourceAccessAssignment> findByResourceIdAndUserId(String resourceId, String userId);
}
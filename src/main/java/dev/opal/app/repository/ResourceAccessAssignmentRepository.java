package dev.opal.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.opal.app.entity.ResourceAccessAssignment;

@Repository
public interface ResourceAccessAssignmentRepository extends JpaRepository<ResourceAccessAssignment, Long> {
	Optional<ResourceAccessAssignment> findByResourceIdAndUserId(String resourceId, String userId);
}
package dev.opal.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.opal.app.entity.GroupAccessAssignment;

@Repository
public interface GroupAccessAssignmentRepository extends JpaRepository<GroupAccessAssignment, Long> {

	@Query("SELECT g FROM GroupAccessAssignment g WHERE g.group.id = :groupId AND g.resource.id = :resourceId AND g.accessRole.id = :roleId")
	Optional<GroupAccessAssignment> findByGroupIdAndResourceIdAndRoleId(@Param("groupId") String groupId,
			@Param("resourceId") String resourceId, @Param("roleId") String roleId);

	@Query("SELECT g FROM GroupAccessAssignment g WHERE g.group.id = :groupId AND g.resource.id = :resourceId")
	Optional<GroupAccessAssignment> findByGroupIdAndResourceId(@Param("groupId") String groupId,
			@Param("resourceId") String resourceId);

}

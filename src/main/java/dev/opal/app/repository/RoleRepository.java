package dev.opal.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.opal.app.entity.AccessRole;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<AccessRole, String> {

	Optional<AccessRole> findByName(String roleName);
}
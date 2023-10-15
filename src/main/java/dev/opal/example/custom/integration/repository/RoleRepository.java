package dev.opal.example.custom.integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.opal.example.custom.integration.entity.AccessRole;

@RepositoryRestResource
public interface RoleRepository extends JpaRepository<AccessRole, String> {

	Optional<AccessRole> findByName(String roleName);
}
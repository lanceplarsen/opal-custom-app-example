package dev.opal.example.custom.integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.opal.example.custom.integration.entity.AccessUser;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<AccessUser, String> {
	Optional<AccessUser> findByEmail(String email);
}

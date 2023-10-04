package dev.opal.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.opal.app.entity.AccessUser;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<AccessUser, String> {
}
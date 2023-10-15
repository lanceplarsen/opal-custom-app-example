package dev.opal.example.custom.integration.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import dev.opal.example.custom.integration.entity.AccessResource;

@RepositoryRestResource
public interface ResourceRepository extends JpaRepository<AccessResource, String> {
}
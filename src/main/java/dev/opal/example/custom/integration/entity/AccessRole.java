package dev.opal.example.custom.integration.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "access_levels")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class AccessRole {

	@OneToMany(mappedBy = "accessRole")
	private Set<GroupAccessAssignment> groupAccessAssignments = new HashSet<>();

	@ManyToMany(mappedBy = "roles")
	private Set<AccessResource> resources = new HashSet<>();

	@Id
	private String id;

	@NonNull
	private String name;

	@OneToMany(mappedBy = "accessRole")
	private Set<ResourceAccessAssignment> resourceAccessAssignments = new HashSet<>();

	public AccessRole(String name) {
		this.name = name;
	}

	@PrePersist
	public void ensureIdAssigned() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}

	public void addResource(AccessResource resource) {
		resources.add(resource);
		resource.getRoles().add(this);
	}

	public void removeResource(AccessResource resource) {
		resources.remove(resource);
		resource.getRoles().remove(this);
	}

}
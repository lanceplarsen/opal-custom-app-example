package dev.opal.app.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "resources")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = { "userAccessAssignments", "groupAccessAssignments", "roles" })
@ToString(exclude = { "userAccessAssignments", "groupAccessAssignments", "roles" })
public class AccessResource {

	private String description;

	@OneToMany(mappedBy = "resource")
	private Set<GroupAccessAssignment> groupAccessAssignments = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "resource_role", joinColumns = @JoinColumn(name = "resource_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<AccessRole> roles = new HashSet<>();

	@Id
	private String id;

	@NonNull
	private String name;

	@OneToMany(mappedBy = "resource")
	private Set<ResourceAccessAssignment> userAccessAssignments = new HashSet<>();

	public AccessResource(String name, String description) {
		this.name = name;
		this.description = description;
	}

	@PrePersist
	public void ensureIdAssigned() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(String id) {
		if (this.id != null && !this.id.equals(id)) {
			throw new IllegalStateException("ID cannot be changed once set!");
		}
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addRole(AccessRole role) {
		roles.add(role);
		role.getResources().add(this);
	}

	public void removeRole(AccessRole role) {
		roles.remove(role);
		role.getResources().remove(this);
	}

}
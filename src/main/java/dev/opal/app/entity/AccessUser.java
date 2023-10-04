package dev.opal.app.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = { "resources", "groups" })
@ToString(exclude = { "resources", "groups" })
public class AccessUser {

	@Id
	private String id;

	@NonNull
	private String email;

	@ManyToMany(mappedBy = "users")
	private Set<AccessResource> resources = new HashSet<>();

	@ManyToMany(mappedBy = "users")
	private Set<AccessGroup> groups = new HashSet<>();

	public AccessUser(String email) {
		this.email = email;
	}

	public AccessUser(String id, String email) {
		setId(id); // Using setId method for protection
		this.email = email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void addResource(AccessResource resource) {
		this.resources.add(resource);
		resource.getUsers().add(this);
	}

	public void removeResource(AccessResource resource) {
		this.resources.remove(resource);
		resource.getUsers().remove(this);
	}

	public void addGroup(AccessGroup group) {
		this.groups.add(group);
		group.getUsers().add(this);
	}

	public void removeGroup(AccessGroup group) {
		this.groups.remove(group);
		group.getUsers().remove(this);
	}

	@PrePersist
	public void ensureIdAssigned() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}

	public void setId(String id) {
		if (this.id != null && !this.id.equals(id)) {
			throw new IllegalStateException("ID cannot be changed once set!");
		}
		this.id = id;
	}

}
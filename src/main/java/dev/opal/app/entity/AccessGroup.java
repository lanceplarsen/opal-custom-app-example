package dev.opal.app.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
@Table(name = "groups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = { "users", "resources" })
@ToString(exclude = { "users", "resources" })
public class AccessGroup {

	@Id
	private String id;

	@NonNull
	private String name;

	private String description;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "group_user", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<AccessUser> users = new HashSet<>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "group_resource", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
	private Set<AccessResource> resources = new HashSet<>();

	public AccessGroup(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public AccessGroup(String id, String name, String description) {
		setId(id); // Using setId method for protection
		this.name = name;
		this.description = description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void addUser(AccessUser user) {
		this.users.add(user);
		user.getGroups().add(this);
	}

	public void removeUser(AccessUser user) {
		this.users.remove(user);
		user.getGroups().remove(this);
	}

	public void addResource(AccessResource resource) {
		this.resources.add(resource);
		resource.getGroups().add(this);
	}

	public void removeResource(AccessResource resource) {
		this.resources.remove(resource);
		resource.getGroups().remove(this);
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
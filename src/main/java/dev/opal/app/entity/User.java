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
public class User {

	@Id
	private String id;

	@NonNull
	private String email;

	@ManyToMany(mappedBy = "users")
	private Set<Resource> resources = new HashSet<>();

	@ManyToMany(mappedBy = "users")
	private Set<Group> groups = new HashSet<>();

	public User(String email) {
		this.email = email;
	}

	public User(String id, String email) {
		setId(id); // Using setId method for protection
		this.email = email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void addResource(Resource resource) {
		this.resources.add(resource);
		resource.getUsers().add(this);
	}

	public void removeResource(Resource resource) {
		this.resources.remove(resource);
		resource.getUsers().remove(this);
	}

	public void addGroup(Group group) {
		this.groups.add(group);
		group.getUsers().add(this);
	}

	public void removeGroup(Group group) {
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
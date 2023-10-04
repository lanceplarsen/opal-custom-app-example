package dev.opal.app.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
@Table(name = "resources")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = { "groups", "users" })
@ToString(exclude = { "groups", "users" })
public class AccessResource {

	@Id
	private String id;

	@NonNull
	private String name;

	private String description;

	@ManyToMany(mappedBy = "resources")
	private Set<AccessGroup> groups = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "resource_user", joinColumns = @JoinColumn(name = "resource_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<AccessUser> users = new HashSet<>();

	public AccessResource(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public AccessResource(String id, String name, String description) {
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

	public void addGroup(AccessGroup group) {
		this.groups.add(group);
		group.getResources().add(this);
	}

	public void removeGroup(AccessGroup group) {
		this.groups.remove(group);
		group.getResources().remove(this);
	}

	public void addUser(AccessUser user) {
		this.users.add(user);
		user.getResources().add(this);
	}

	public void removeUser(AccessUser user) {
		this.users.remove(user);
		user.getResources().remove(this);
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
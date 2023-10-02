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
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "resources")
@EqualsAndHashCode(exclude = { "groups", "users" })
@ToString(exclude = { "groups", "users" })
public class Resource {

	@Id
	private String id;

	private String name;
	private String description;

	@ManyToMany(mappedBy = "resources")
	private Set<Group> groups = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "resource_user", joinColumns = @JoinColumn(name = "resource_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();

	public Resource(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public void addGroup(Group group) {
		this.groups.add(group);
		group.getResources().add(this);
	}

	public void removeGroup(Group group) {
		this.groups.remove(group);
		group.getResources().remove(this);
	}

	@PrePersist
	public void ensureIdAssigned() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}
}
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
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "groups")
@EqualsAndHashCode(exclude = { "users", "resources" })
@ToString(exclude = { "users", "resources" })
public class Group {

	@Id
	private String id;

	@NotNull
	private String name;

	private String description;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "group_user", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "group_resource", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "resource_id"))
	private Set<Resource> resources = new HashSet<>();

	public Group(String name) {
		this.name = name;
	}

	public void addResource(Resource resource) {
		this.resources.add(resource);
		resource.getGroups().add(this);
	}

	public void removeResource(Resource resource) {
		this.resources.remove(resource);
		resource.getGroups().remove(this);
	}

	@PrePersist
	public void ensureIdAssigned() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}
}
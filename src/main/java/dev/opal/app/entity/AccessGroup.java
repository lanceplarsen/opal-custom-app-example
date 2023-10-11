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
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "groups")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = { "users", "resourceAssignments" })
@ToString(exclude = { "users", "resourceAssignments" })
public class AccessGroup {

	private String description;

	@Id
	private String id;

	@NonNull
	private String name;

	@Setter
	@OneToMany(mappedBy = "group")
	private Set<GroupAccessAssignment> resourceAssignments = new HashSet<>();

	@ManyToMany
	@JoinTable(name = "user_group_assignments", joinColumns = @JoinColumn(name = "group_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<AccessUser> users = new HashSet<>();

	public AccessGroup(String name, String description) {
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

	public void addResourceAssignment(AccessResource resource, AccessRole role) {
		GroupAccessAssignment assignment = new GroupAccessAssignment();
		assignment.setGroup(this);
		assignment.setResource(resource);
		assignment.setAccessRole(role);

		this.resourceAssignments.add(assignment);
	}

	public void removeResourceAssignment(AccessResource resource, AccessRole role) {
		GroupAccessAssignment targetAssignment = null;
		for (GroupAccessAssignment assignment : this.resourceAssignments) {
			if (assignment.getResource().equals(resource)
					&& (role == null || assignment.getAccessRole().equals(role))) {
				targetAssignment = assignment;
				break;
			}
		}
		if (targetAssignment != null) {
			this.resourceAssignments.remove(targetAssignment);
		}
	}

}
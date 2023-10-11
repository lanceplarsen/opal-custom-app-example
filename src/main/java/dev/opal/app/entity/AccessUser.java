package dev.opal.app.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public class AccessUser {

	@NonNull
	private String email;

	@ManyToMany(mappedBy = "users")
	private Set<AccessGroup> groups = new HashSet<>();

	@Id
	private String id;

	@OneToMany(mappedBy = "user")
	private Set<ResourceAccessAssignment> resourceAccessAssignments = new HashSet<>();

	public AccessUser(String email) {
		this.email = email;
	}

	public AccessUser(String id, String email) {
		setId(id); // Using setId method for protection
		this.email = email;
	}
	// No need to define relationship with AccessGroup directly as it's established
	// through the ResourceAccessAssignment

	@PrePersist
	public void ensureIdAssigned() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(String id) {
		if (this.id != null && !this.id.equals(id)) {
			throw new IllegalStateException("ID cannot be changed once set!");
		}
		this.id = id;
	}
}
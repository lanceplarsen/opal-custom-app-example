package dev.opal.app.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
@EqualsAndHashCode(exclude = { "resources", "groups" })
@ToString(exclude = { "resources", "groups" })
public class User {

	@Id
	private String id;

	@NotNull
	@Email
	private String email;

	@ManyToMany(mappedBy = "users")
	private Set<Resource> resources = new HashSet<>();

	@ManyToMany(mappedBy = "users")
	private Set<Group> groups = new HashSet<>();

	@PrePersist
	public void ensureIdAssigned() {
		if (this.id == null) {
			this.id = UUID.randomUUID().toString();
		}
	}
}
package dev.opal.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "resource_access_assignments")
@Getter
public class ResourceAccessAssignment {

	@ManyToOne
	@JoinColumn(name = "access_role_id")
	@Setter
	private AccessRole accessRole;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "resource_id")
	@Setter
	private AccessResource resource;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@Setter
	private AccessUser user;
}

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
@Table(name = "group_access_assignments")
@Getter
public class GroupAccessAssignment {

	@ManyToOne
	@JoinColumn(name = "access_role_id")
	@Setter
	private AccessRole accessRole;

	@ManyToOne
	@JoinColumn(name = "group_id")
	@Setter
	private AccessGroup group;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "resource_id")
	@Setter
	private AccessResource resource;
}

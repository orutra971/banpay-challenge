package com.banpay.challenge.banpaychallenge.models;

import com.banpay.challenge.banpaychallenge.security.services.RoleService;
import jakarta.persistence.*;

/**
 * This entity class represents a Role within the application.
 * Each Role instance contains properties such as id and name.
 * The name is an enumerator that defines the role type.
 *
 * The Role class corresponds to the "roles" table in the database.
 */
@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;

	/**
	 * Default constructor for the Role class.
	 */
	public Role() {
		//
	}

	/**
	 * Constructs a new Role object with the given role name.
	 * The role name is converted to an ERole enum value using the RoleService.getRoleFromName() method.
	 *
	 * @param role The role name to be assigned to the new Role object.
	 * @see RoleService#getRoleFromName(String)
	 */
	public Role(String role) {
		this.name = RoleService.getRoleFromName(role);
	}

	/**
	 * Parameterized constructor for the Role class.
	 *
	 * @param name The name of the Role
	 */
	public Role(ERole name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ERole getName() {
		return name;
	}

	public void setName(ERole name) {
		this.name = name;
	}
}
package com.evolution.repository.projection;

import com.evolution.model.Status;
import com.evolution.model.TypeUser;

public class Users {

	private Long id;
	private String name;
	private String email;
	private TypeUser typeUser;
	private Status status;

	public Users() {
		super();

	}

	public Users(Long id, String name, String email, TypeUser typeUser, Status status) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.typeUser = typeUser;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TypeUser getTypeUser() {
		return typeUser;
	}

	public void setTypeUser(TypeUser typeUser) {
		this.typeUser = typeUser;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}

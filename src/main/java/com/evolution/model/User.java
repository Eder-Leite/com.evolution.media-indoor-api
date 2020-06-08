package com.evolution.model;

import java.util.Date;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
@SuppressWarnings("deprecation")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_user")
	@SequenceGenerator(sequenceName = "sequence_user", allocationSize = 1, name = "sequence_user")
	private Long id;

	@Email(message = "Attention the informed email does not meet the standards")
	@NotBlank(message = "is required")
	@Column(unique = true, nullable = false)
	private String email;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TypeUser typeUser = TypeUser.USER;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status = Status.ACTIVE;

	private Date creationDate = new Date();

	private Date updateDate = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (!StringUtils.isEmpty(password)) {
			password = this.encodePassword(password);
		}

		this.password = password;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String encodePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", password=" + password + ", typeUser="
				+ typeUser + ", status=" + status + ", creationDate=" + creationDate + ", updateDate=" + updateDate
				+ "]";
	}

}

package com.evolution.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "GROUPS")
@SuppressWarnings("deprecation")
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_groups")
	@SequenceGenerator(sequenceName = "sequence_groups", allocationSize = 1, name = "sequence_groups")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@NotBlank(message = "is required")
	@Column(nullable = false)
	private String description;

	private Date creationDate = new Date();

	private Date updateDate = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		Group other = (Group) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", user=" + user + ", description=" + description + ", creationDate=" + creationDate
				+ ", updateDate=" + updateDate + "]";
	}

}

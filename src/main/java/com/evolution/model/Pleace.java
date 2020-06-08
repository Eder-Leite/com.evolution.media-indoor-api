package com.evolution.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PLEACES")
public class Pleace {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_pleaces")
	@SequenceGenerator(sequenceName = "sequence_pleaces", allocationSize = 1, name = "sequence_pleaces")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "GROUP_ID", nullable = false)
	private Group group;

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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
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
		Pleace other = (Pleace) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pleace [id=" + id + ", user=" + user + ", group=" + group + ", creationDate=" + creationDate
				+ ", updateDate=" + updateDate + "]";
	}

}

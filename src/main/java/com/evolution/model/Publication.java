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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PUBLICATIOS")
public class Publication {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_publications")
	@SequenceGenerator(sequenceName = "sequence_publications", allocationSize = 1, name = "sequence_publications")
	private Long id;

	@NotNull(message = "is required")
	private Long ordination;

	@ManyToOne
	@JoinColumn(name = "GROUP_ID", nullable = true)
	private Group group;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = true)
	private User user;

	@ManyToOne
	@JoinColumn(name = "MEDIA_ID", nullable = false)
	private Media media;

	private Date creationDate = new Date();

	private Date updateDate = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrdination() {
		return ordination;
	}

	public void setOrdination(Long ordination) {
		this.ordination = ordination;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
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
		Publication other = (Publication) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Publication [id=" + id + ", ordination=" + ordination + ", group=" + group + ", user=" + user
				+ ", media=" + media + ", creationDate=" + creationDate + ", updateDate=" + updateDate + "]";
	}

}

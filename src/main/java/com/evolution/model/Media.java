package com.evolution.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "MEDIAS")
@SuppressWarnings("deprecation")
public class Media {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_medias")
	@SequenceGenerator(sequenceName = "sequence_medias", allocationSize = 1, name = "sequence_medias")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TypeMedia type = TypeMedia.FORESCAT;

	@NotBlank(message = "is required")
	@Column(nullable = false)
	private String title;

	private Long city;

	@NotBlank(message = "is required")
	@Column(nullable = false)
	private String directory;

	@NotNull(message = "is required")
	private Long duration = 60l;

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

	public TypeMedia getType() {
		return type;
	}

	public void setType(TypeMedia type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCity() {
		return city;
	}

	public void setCity(Long city) {
		this.city = city;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
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
		Media other = (Media) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Media [id=" + id + ", user=" + user + ", type=" + type + ", title=" + title + ", city=" + city
				+ ", directory=" + directory + ", duration=" + duration + ", creationDate=" + creationDate
				+ ", updateDate=" + updateDate + "]";
	}

}

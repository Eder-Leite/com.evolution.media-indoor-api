package com.evolution.repository.projection;

import java.math.BigDecimal;

public class Medias {
	private BigDecimal id;
	private BigDecimal ordination;
	private BigDecimal mediaId;
	private BigDecimal userId;
	private BigDecimal groupId;
	private String typeMedia;
	private String title;
	private BigDecimal city;
	private String directory;
	private BigDecimal duration;
	private BigDecimal users;

	public Medias() {
		super();
	}

	public Medias(Object obj) {
		Object[] objeto = (Object[]) obj;
		this.id = (BigDecimal) objeto[0];
		this.ordination = (BigDecimal) objeto[1];
		this.mediaId = (BigDecimal) objeto[2];
		this.userId = (BigDecimal) objeto[3];
		this.groupId = (BigDecimal) objeto[4];
		this.typeMedia = (String) objeto[5];
		this.title = (String) objeto[6];
		this.city = (BigDecimal) objeto[7];
		this.directory = (String) objeto[8];
		this.duration = (BigDecimal) objeto[9];
		this.users = (BigDecimal) objeto[10];
	}

	public Medias(BigDecimal id, BigDecimal ordination, BigDecimal mediaId, BigDecimal userId, BigDecimal groupId,
			String typeMedia, String title, BigDecimal city, String directory, BigDecimal duration, BigDecimal users) {
		super();
		this.id = id;
		this.ordination = ordination;
		this.mediaId = mediaId;
		this.userId = userId;
		this.groupId = groupId;
		this.typeMedia = typeMedia;
		this.title = title;
		this.city = city;
		this.directory = directory;
		this.duration = duration;
		this.users = users;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public BigDecimal getOrdination() {
		return ordination;
	}

	public void setOrdination(BigDecimal ordination) {
		this.ordination = ordination;
	}

	public BigDecimal getMediaId() {
		return mediaId;
	}

	public void setMediaId(BigDecimal mediaId) {
		this.mediaId = mediaId;
	}

	public BigDecimal getUserId() {
		return userId;
	}

	public void setUserId(BigDecimal userId) {
		this.userId = userId;
	}

	public BigDecimal getGroupId() {
		return groupId;
	}

	public void setGroupId(BigDecimal groupId) {
		this.groupId = groupId;
	}

	public String getTypeMedia() {
		return typeMedia;
	}

	public void setTypeMedia(String typeMedia) {
		this.typeMedia = typeMedia;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getCity() {
		return city;
	}

	public void setCity(BigDecimal city) {
		this.city = city;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public BigDecimal getDuration() {
		return duration;
	}

	public void setDuration(BigDecimal duration) {
		this.duration = duration;
	}

	public BigDecimal getUsers() {
		return users;
	}

	public void setUsers(BigDecimal users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Medias [id=" + id + ", ordination=" + ordination + ", mediaId=" + mediaId + ", userId=" + userId
				+ ", groupId=" + groupId + ", typeMedia=" + typeMedia + ", title=" + title + ", city=" + city
				+ ", directory=" + directory + ", duration=" + duration + ", users=" + users + "]";
	}

}

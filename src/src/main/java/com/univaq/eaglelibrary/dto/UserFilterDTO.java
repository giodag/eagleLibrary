package com.univaq.eaglelibrary.dto;

import java.util.List;

import com.univaq.eaglelibrary.utility.Permission;

public class UserFilterDTO {
	
	private List<Long> id;
	private Enum<Permission> permission;
	private Integer level;
	private Boolean transcriber;
	
	//-- Getter and Setter --//
	
	public Enum<Permission> getPermission() {
		return permission;
	}
	public void setPermission(Enum<Permission> permission) {
		this.permission = permission;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Boolean getTranscriber() {
		return transcriber;
	}
	public void setTranscriber(Boolean transcriber) {
		this.transcriber = transcriber;
	}
	public List<Long> getId() {
		return id;
	}
	public void setId(List<Long> id) {
		this.id = id;
	}
}

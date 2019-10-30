package com.univaq.eaglelibrary.dto;

import java.util.List;

public class UnassignTranscriptionRequestDTO {
	
	private String username;
	private List<PageDTO> pageList;
	
	//-- Getter and Setter --//
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<PageDTO> getPageList() {
		return pageList;
	}
	public void setPageList(List<PageDTO> pageList) {
		this.pageList = pageList;
	}
	
}

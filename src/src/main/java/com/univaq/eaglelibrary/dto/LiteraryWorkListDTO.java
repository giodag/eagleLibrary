package com.univaq.eaglelibrary.dto;

import java.util.List;

public class LiteraryWorkListDTO {
	
	List<LiteraryWorkDTO> literaryWorkList;

	//-- Getter and Setter --//
	
	public List<LiteraryWorkDTO> getLiteraryWorkList() {
		return literaryWorkList;
	}

	public void setLiteraryWorkList(List<LiteraryWorkDTO> literaryWorkList) {
		this.literaryWorkList = literaryWorkList;
	}
}

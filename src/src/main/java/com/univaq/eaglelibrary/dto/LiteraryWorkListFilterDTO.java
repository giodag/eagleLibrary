package com.univaq.eaglelibrary.dto;

import java.util.Date;
import java.util.List;

public class LiteraryWorkListFilterDTO {
	
	private List<Long> idList;
	private String category;
	private String title;
	private Integer year;
	private String author;
	private String partOfText;
	
	//-- Getter and Setter --//
	
	public List<Long> getIdList() {
		return idList;
	}
	public void setIdList(List<Long> ids) {
		this.idList = ids;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPartOfText() {
		return partOfText;
	}
	public void setPartOfText(String partOfText) {
		this.partOfText = partOfText;
	}

}

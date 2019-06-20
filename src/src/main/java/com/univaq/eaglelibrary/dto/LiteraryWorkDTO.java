package com.univaq.eaglelibrary.dto;

import java.util.Date;
import java.util.List;

public class LiteraryWorkDTO {

	private Long id;
	private String category;
	private String title;
	private Date year;
	private String author;
	private List<PageDTO> pageList;
	
	//-- Getter and Setter --//
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Date getYear() {
		return year;
	}
	public void setYear(Date year) {
		this.year = year;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<PageDTO> getPageList() {
		return pageList;
	}
	public void setPageList(List<PageDTO> pageList) {
		this.pageList = pageList;
	}
	
}

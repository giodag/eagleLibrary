package com.univaq.eaglelibrary.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "literaryWork")
public class LiteraryWork {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "category")
	private String category;

	@Column(name = "title")
	private String title;

	@Column(name = "year")
	private Date year;

	@Column(name = "author")
	private String author;

	@OneToMany(mappedBy = "literaryWork", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	private List<Page> pageList;

	//--Getter and Setter --//
	
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
}

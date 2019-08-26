package com.univaq.eaglelibrary.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "literary_work")
public class Page {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "pageNumber")
	private Integer pageNumber;

	@Column(name = "chapter")
	private Integer chapter;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_transcription")
	private Transcription transcription;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	LiteraryWork literaryWork;

	// -- Getter and Setter --//

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getChapter() {
		return chapter;
	}

	public void setChapter(Integer chapter) {
		this.chapter = chapter;
	}

}

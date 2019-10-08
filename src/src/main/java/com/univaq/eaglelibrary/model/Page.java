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
@Table(name = "page")
public class Page {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "pageNumber")
	private Integer pageNumber;

	@Column(name = "chapter")
	private Integer chapter;
	
	@Column(name = "image")
	private byte[] image;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transcription_id")
	private Transcription transcription;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "literaryWork_id")
	LiteraryWork literaryWorkPage;

	// -- Getter and Setter --//
	
	public Transcription getTranscription() {
		return transcription;
	}

	public void setTranscription(Transcription transcription) {
		this.transcription = transcription;
	}

	public LiteraryWork getLiteraryWorkPage() {
		return literaryWorkPage;
	}

	public void setLiteraryWorkPage(LiteraryWork literaryWorkPage) {
		this.literaryWorkPage = literaryWorkPage;
	}

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

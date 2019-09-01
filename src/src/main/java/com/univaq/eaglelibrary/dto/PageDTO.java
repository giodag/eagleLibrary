package com.univaq.eaglelibrary.dto;

public class PageDTO {
	
	private Long id;
	private Integer pageNumber;
	private Integer chapter;
	private TranscriptionDTO transcriptionDTO;
	private Long idLiteraryWork;
	
	//-- Getter and Setter
	
	public Long getIdLiteraryWork() {
		return idLiteraryWork;
	}
	public void setIdLiteraryWork(Long idLiteraryWork) {
		this.idLiteraryWork = idLiteraryWork;
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
	public TranscriptionDTO getTranscriptionDTO() {
		return transcriptionDTO;
	}
	public void setTranscriptionDTO(TranscriptionDTO transcriptionDTO) {
		this.transcriptionDTO = transcriptionDTO;
	}
	
}

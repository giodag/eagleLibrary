package com.univaq.eaglelibrary.view;

import com.univaq.eaglelibrary.dto.LiteraryWorkDTO;
import com.univaq.eaglelibrary.dto.TranscriptionDTO;

public class TranscriptionTable {

	private String author;
	private String title;
	private String year;
	private String status;
	private String page;

	public TranscriptionTable(LiteraryWorkDTO literaryWorkDTO) {
		if(literaryWorkDTO != null) {
			author = literaryWorkDTO.getAuthor();
			title = literaryWorkDTO.getTitle();
			if(literaryWorkDTO.getYear() != null) {
				year = literaryWorkDTO.getYear().toString();
			}
		}
	}
	
	public TranscriptionTable(TranscriptionDTO transcription) {
		if(transcription != null) {
			status = transcription.getStatus();
			if(transcription.getLiteraryWork() != null) {
				author = transcription.getLiteraryWork().getAuthor();
				title = transcription.getLiteraryWork().getTitle();
				if(transcription.getLiteraryWork().getYear() != null) {
					year = transcription.getLiteraryWork().getYear().toString();
				}
			}
			if(transcription.getPage() != null && transcription.getPage().getPageNumber() != null) {
				page = transcription.getPage().getPageNumber().toString();
			}
		}		
	}
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
}

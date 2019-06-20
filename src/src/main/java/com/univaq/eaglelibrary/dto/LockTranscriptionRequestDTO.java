package com.univaq.eaglelibrary.dto;

public class LockTranscriptionRequestDTO {

	private String username;
	private TranscriptionDTO transcription;
	
	//-- Getter and Setter --//
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public TranscriptionDTO getTranscription() {
		return transcription;
	}
	public void setTranscription(TranscriptionDTO transcription) {
		this.transcription = transcription;
	}
	
}

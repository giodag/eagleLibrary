package com.univaq.eaglelibrary.dto;

public class UnassignTranscriptionResponseDTO {

	private Boolean assigned;
	private String message;
	
	//-- Getter and Setter --//
	
	public Boolean getAssigned() {
		return assigned;
	}
	public void setAssigned(Boolean assigned) {
		this.assigned = assigned;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}

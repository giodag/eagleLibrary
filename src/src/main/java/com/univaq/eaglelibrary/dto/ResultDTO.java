package com.univaq.eaglelibrary.dto;

public class ResultDTO {
	
	public ResultDTO() {
		super();
	}
	
	public ResultDTO(Boolean successfullyOperation) {
		super();
		this.successfullyOperation = successfullyOperation;
	}

	private Boolean successfullyOperation;
	private String message;
	
	//-- Getter and Setter --//
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getSuccessfullyOperation() {
		return successfullyOperation;
	}

	public void setSuccessfullyOperation(Boolean successfullyOperation) {
		this.successfullyOperation = successfullyOperation;
	}
}

package com.univaq.eaglelibrary.persistence.exceptions;

public class MandatoryFieldException extends Exception{

	private static final long serialVersionUID = 1L;

	public MandatoryFieldException(){
	}
	
	public MandatoryFieldException(String message){
		super(message);
	}
	
	public MandatoryFieldException(Throwable throwable){
		super(throwable);
	}
	
	public MandatoryFieldException(String message, Throwable throwable){
		super(message, throwable);
	}

}

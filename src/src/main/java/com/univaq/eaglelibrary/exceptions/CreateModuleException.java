package com.univaq.eaglelibrary.exceptions;

public class CreateModuleException extends Exception{

	private static final long serialVersionUID = 1L;

	public CreateModuleException(){
	}
	
	public CreateModuleException(String message){
		super(message);
	}
	
	public CreateModuleException(Throwable throwable){
		super(throwable);
	}
	
	public CreateModuleException(String message, Throwable throwable){
		super(message, throwable);
	}
}

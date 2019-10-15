package com.univaq.eaglelibrary.exceptions;

public class CreateUserException extends Exception{

	private static final long serialVersionUID = 1L;

	public CreateUserException(){
	}
	
	public CreateUserException(String message){
		super(message);
	}
	
	public CreateUserException(Throwable throwable){
		super(throwable);
	}
	
	public CreateUserException(String message, Throwable throwable){
		super(message, throwable);
	}

}

package com.univaq.eaglelibrary.exceptions;

public class UserNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message){
		super(message);
	}
	
	public UserNotFoundException(Throwable throwable){
		super(throwable);
	}
	
	public UserNotFoundException(String message, Throwable throwable){
		super(message, throwable);
	}
	
}

package com.univaq.eaglelibrary.persistence.exceptions;

public class WrongPasswordException extends Exception{

	private static final long serialVersionUID = 1L;

	public WrongPasswordException(String message){
		super(message);
	}
	
	public WrongPasswordException(Throwable throwable){
		super(throwable);
	}
	
	public WrongPasswordException(String message, Throwable throwable){
		super(message, throwable);
	}
}

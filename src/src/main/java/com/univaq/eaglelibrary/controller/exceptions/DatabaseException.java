package com.univaq.eaglelibrary.controller.exceptions;

public class DatabaseException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DatabaseException(){
	}
	
	public DatabaseException(String message){
		super(message);
	}
	
	public DatabaseException(Throwable throwable){
		super(throwable);
	}
	
	public DatabaseException(String message, Throwable throwable){
		super(message, throwable);
	}

}

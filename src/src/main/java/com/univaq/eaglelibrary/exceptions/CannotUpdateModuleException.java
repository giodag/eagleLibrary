package com.univaq.eaglelibrary.exceptions;

public class CannotUpdateModuleException extends Exception{
	
	private static final String ERROR = "It is impossible to submit the request at the moment. \r\n" + 
			"Please try again in {} days.";

	private static final long serialVersionUID = 1L;

	public CannotUpdateModuleException(){
	}
	
	public CannotUpdateModuleException(String message){
		super(message);
	}
	
	public CannotUpdateModuleException(Throwable throwable){
		super(throwable);
	}
	
	public CannotUpdateModuleException(String message, Throwable throwable){
		super(message, throwable);
	}

}

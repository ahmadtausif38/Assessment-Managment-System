package com.example.exception;

public class DataAlreadyExistException extends RuntimeException {
	
	public DataAlreadyExistException(){
		
	}
	
	public DataAlreadyExistException(String message){
		super(message);
		
	}

}

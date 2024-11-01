package com.example.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.exception.DataAlreadyExistException;
import com.example.exception.DataNotFoundException;
import com.example.exception.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	

	@ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<?> handleDataAlreadyExistExceptions(DataAlreadyExistException ex) {
		 ErrorResponse errorResponse = new ErrorResponse( HttpStatus.CONFLICT.value(),"An unexpected error occurred: " + ex.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	    }
	
	
	@ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleDataNotFoundExceptions(DataNotFoundException ex) {
		 ErrorResponse errorResponse = new ErrorResponse( HttpStatus.NOT_FOUND.value(),"An unexpected error occurred: " + ex.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	    }
	
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllExceptions(Exception ex) {
		 ErrorResponse errorResponse = new ErrorResponse( HttpStatus.INTERNAL_SERVER_ERROR.value(),"An unexpected error occurred: " + ex.getMessage());
	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	    }

}

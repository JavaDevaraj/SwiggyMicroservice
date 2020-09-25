package com.stackroute.swiggy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class CustomerExceptionControllerAdvice {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<?> handleRequestException(CustomerNotFoundException ex){
		return new ResponseEntity("Swiggy api service CustomerNotFoundException - "+ex.getMessage(), HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(OrderNotFound.class)
	public ResponseEntity<?> handleRequestException(OrderNotFound ex){
		return new ResponseEntity("Swiggy api service OrderNotFound - "+ex.getMessage(), HttpStatus.BAD_REQUEST);
		
	}

}

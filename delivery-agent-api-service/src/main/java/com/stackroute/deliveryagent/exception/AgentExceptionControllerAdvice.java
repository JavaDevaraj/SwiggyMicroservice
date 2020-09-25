package com.stackroute.deliveryagent.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AgentExceptionControllerAdvice {
	
	@ExceptionHandler(OrderNotFound.class)
	public ResponseEntity<?> handleRequestException(OrderNotFound ex){
		
		return new ResponseEntity("Delivery Agent not able to find the "+ex.getMessage(), HttpStatus.BAD_REQUEST);
		
	}

}

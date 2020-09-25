package com.stackroute.restaurantpartner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class RestaurantExceptionControllerAdvice {
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<?> handleRequestException(OrderNotFoundException ex){
		return new ResponseEntity("Restuarant Partner not able to find the "+ex.getMessage(), HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(RestaurantNotExist.class)
	public ResponseEntity<?> handleRequestException(RestaurantNotExist ex){
		return new ResponseEntity("Restuarant Partner not able to find the "+ex.getMessage(), HttpStatus.BAD_REQUEST);
		
	}

}

package com.stackroute.swiggy.exception;

public class OrderNotFound extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public OrderNotFound() {
		
	}
	
	public OrderNotFound(String errorMsg) {
		super(errorMsg);
	}
	
}

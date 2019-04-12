package com.shoppingcart.anzdemo.exceptions;

public class CustomerNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CustomerNotFoundException(String email){
		super("Customer "+email+" does not exist. Need to create one...");
	}
	
	public CustomerNotFoundException(Long custId){
		super("Customer "+custId+" does not exist. Need to create one...");
	}
	
}

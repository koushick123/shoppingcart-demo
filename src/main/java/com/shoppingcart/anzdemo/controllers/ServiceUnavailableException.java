package com.shoppingcart.anzdemo.controllers;

public class ServiceUnavailableException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ServiceUnavailableException(String serviceName) {
		super(serviceName+" Unavailable");
	}

}

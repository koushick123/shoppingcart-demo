package com.shoppingcart.anzdemo.exceptions;

public class InventoryCreateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InventoryCreateException(String message){
		super("Unable to add new Inventory due to following - "+message);
	}
}

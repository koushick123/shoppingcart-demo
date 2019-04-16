package com.shoppingcart.anzdemo.exceptions;

public class InventoryNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InventoryNotFoundException(String invId){
		super("Inventory "+invId+" not found");
	}
}

package com.shoppingcart.anzdemo.exceptions;

public class PurchaseInCompleteException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PurchaseInCompleteException(Long invId, Long custId, String error){
		super("Purchase of inventory "+invId+" was not complete for customer "+custId+" due to "+error);
	}
}

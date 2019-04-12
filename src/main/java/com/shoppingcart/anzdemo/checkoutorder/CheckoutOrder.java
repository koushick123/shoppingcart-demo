package com.shoppingcart.anzdemo.checkoutorder;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_ORDER_CHECKOUT")
public class CheckoutOrder implements Serializable {

	/**
	 * 
	 */
	static Long nextOrderId = 1000L;
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ORDER_ID")
	Long orderId;
	
	@Column(name="ORDER_DATE")
	Date orderDate;
	
	@Column(name="INV_ID")
	Long invId;
	
	@Column(name="CUST_ID")
	Long custId;
	
	/*
	 * To be done by DB sequence ideally
	 */
	public static Long getNextCheckoutOrderId(){
		synchronized (nextOrderId) {
			return ++nextOrderId;
		}
	}
}

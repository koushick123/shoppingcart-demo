package com.shoppingcart.anzdemo.checkoutorder;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("checkout")
public class CheckoutOrderDTO {
	
	Long orderId;
	Date orderDate;	
	Long invId;	
	Long custId;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Long getInvId() {
		return invId;
	}
	public void setInvId(Long invId) {
		this.invId = invId;
	}
	public Long getCustId() {
		return custId;
	}
	public void setCustId(Long custId) {
		this.custId = custId;
	}
}

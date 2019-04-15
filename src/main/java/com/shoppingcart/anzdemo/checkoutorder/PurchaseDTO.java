package com.shoppingcart.anzdemo.checkoutorder;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("purchase")
public class PurchaseDTO {

	String email;
	Long invId;
	String name;
	String description;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getInvId() {
		return invId;
	}
	public void setInvId(Long invId) {
		this.invId = invId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}

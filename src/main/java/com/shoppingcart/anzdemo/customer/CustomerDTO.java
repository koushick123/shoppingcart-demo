package com.shoppingcart.anzdemo.customer;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("customer")
public class CustomerDTO {
	
	Long id;
	String email;
	String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}

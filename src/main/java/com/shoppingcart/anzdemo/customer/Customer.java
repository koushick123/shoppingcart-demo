package com.shoppingcart.anzdemo.customer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_CUSTOMER")
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Long nextId = 1003L;
	@Id
	@Column(name="CUST_ID")
	Long id;
	
	@Column
	String email;
	@Column
	String name;
	
	/* 
	 * Ideally to be done using DB sequences.
	 */
	public static Long getCustId(){
		synchronized(nextId){
			return ++nextId;
		}
	}
	/*
	 * Default constructor needed for JPA
	 */
	public Customer(){
		id = Long.MAX_VALUE;
		email = "random@gmail.com";
		name = "Random";
	}
	
	public Customer(Long cusId, String mail, String nam){
		id = cusId;
		email = mail;
		name = nam;
	}

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

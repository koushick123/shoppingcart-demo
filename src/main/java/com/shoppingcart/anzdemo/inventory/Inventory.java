package com.shoppingcart.anzdemo.inventory;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="T_INVENTORY")
public class Inventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="INV_ID")
	Long invId;
	
	@Column
	String name;
	
	@Column
	String description;

	public Inventory(Long id, String nam, String desc){
		invId = id;
		name = nam;
		description = desc;
	}

	public Inventory(){
		invId = Long.MAX_VALUE;
		name="Inventory";
		description="Inventory Desc";
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

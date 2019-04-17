package com.shoppingcart.anzdemo.inventory;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("inventory")
public class InventoryDTO {
	
	Long invId;
	String name;
	String description;
	
	public InventoryDTO(Long id, String nam, String desc){
		invId = id;
		nam = name;
		description = desc;
	}
	
	private static Long inventoryId = 1005L;
	
	public static Long getNextInventoryId(){
		synchronized (inventoryId){
			return ++inventoryId;
		}
	}
	public InventoryDTO() {
		// TODO Auto-generated constructor stub
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

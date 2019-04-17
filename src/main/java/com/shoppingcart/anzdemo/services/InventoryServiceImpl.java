package com.shoppingcart.anzdemo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.anzdemo.inventory.Inventory;
import com.shoppingcart.anzdemo.inventory.InventoryRepository;
@Service
public class InventoryServiceImpl implements InventoryRepository{

	@Autowired
	InventoryRepository invRepo;
	
	@Override
	public Long doesInventoryExist(Long invId) {
		// TODO Auto-generated method stub
		return invRepo.doesInventoryExist(invId);
	}

	@Override
	public Inventory findByNumber(Long invId) {
		// TODO Auto-generated method stub
		return invRepo.findByNumber(invId);
	}

	@Override
	public void addNewInventoryItem(Long invId, String name, String description) {
		// TODO Auto-generated method stub
		invRepo.addNewInventoryItem(invId, name, description);
	}

}

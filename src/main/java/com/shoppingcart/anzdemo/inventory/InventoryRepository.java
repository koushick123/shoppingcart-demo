package com.shoppingcart.anzdemo.inventory;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
@org.springframework.stereotype.Repository
public interface InventoryRepository extends Repository<Inventory, Long>{

	@Query(value="SELECT INV_ID FROM T_INVENTORY WHERE INV_ID = ?1", nativeQuery=true)
	public Long doesInventoryExist(Long invId);
	
	@Query(value="SELECT INV_ID, NAME, DESCRIPTION FROM T_INVENTORY WHERE INV_ID = ?1", nativeQuery=true)
	public Inventory findByNumber(Long invId);
}

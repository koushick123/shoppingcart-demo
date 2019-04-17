package com.shoppingcart.anzdemo.inventory;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
@org.springframework.stereotype.Repository
public interface InventoryRepository extends Repository<Inventory, Long>{

	@Query(value="SELECT INV_ID FROM T_INVENTORY WHERE INV_ID = ?1", nativeQuery=true)
	public Long doesInventoryExist(Long invId);
	
	@Query(value="SELECT INV_ID, NAME, DESCRIPTION FROM T_INVENTORY WHERE INV_ID = ?1", nativeQuery=true)
	public Inventory findByNumber(Long invId);
	
	@Modifying
	@Query(value="INSERT INTO T_INVENTORY(INV_ID, NAME, DESCRIPTION) VALUES "
			+ "(:invId, :name, :description)", nativeQuery=true)
	@Transactional
	public void addNewInventoryItem(@Param("invId") Long invId, @Param("name") String name, 
			@Param("description") String description);
}

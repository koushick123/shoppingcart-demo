package com.shoppingcart.anzdemo.checkoutorder;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
@org.springframework.stereotype.Repository
public interface CheckoutOrderRepository extends Repository<CheckoutOrder, Long>{

	@Modifying
	@Query(value="INSERT INTO T_ORDER_CHECKOUT(INV_ID, CUST_ID, ORDER_ID, ORDER_DATE) VALUES "
			+ "(:invId, :custId, :orderId, SYSDATE)", nativeQuery=true)
	@Transactional
	public void doOrderCheckout(@Param("invId") Long invId, @Param("custId") Long custId, 
			@Param("orderId") Long orderId);
	
	@Query(value="SELECT CUST_ID, INV_ID, ORDER_ID, ORDER_DATE FROM T_ORDER_CHECKOUT WHERE ORDER_ID = :orderId",
			nativeQuery=true)
	public CheckoutOrder findCheckoutOrderById(@Param("orderId") Long orderId);
}

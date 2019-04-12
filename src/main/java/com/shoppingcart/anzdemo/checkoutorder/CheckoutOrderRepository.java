package com.shoppingcart.anzdemo.checkoutorder;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.shoppingcart.anzdemo.customer.Customer;

public interface CheckoutOrderRepository extends CrudRepository<CheckoutOrder, Long>{

	@Query(value="INSERT INTO T_ORDER_CHECKOUT(INV_ID, CUST_ID, ORDER_ID, ORDER_DATE) VALUES "
			+ "(?1, ?2, ?3, SYSDATE)", nativeQuery=true)
	@Transactional
	public void doOrderCheckout(Long invId, Customer custId, Long orderId);
}

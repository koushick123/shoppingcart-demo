package com.shoppingcart.anzdemo.customer;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Repository
public interface CustomerRepository extends Repository<Customer, Long>{

	@Query(value="SELECT CUST_ID, EMAIL, NAME FROM T_CUSTOMER WHERE EMAIL = ?1", nativeQuery=true)
	public Customer findCustomer(String email);
	
	public List<Customer> findCustomerById(Long id);
		
	@Modifying
	@Query(value="INSERT INTO T_CUSTOMER(CUST_ID, EMAIL, NAME) values (:custId, :email, :name)", nativeQuery=true)
	@Transactional
	public void createNewCustomer(@Param("custId") Long custId, 
			@Param("email") String email, @Param("name") String name);
	
	@Query(value="SELECT CUST_ID, EMAIL, NAME FROM T_CUSTOMER WHERE CUST_ID = ?1", nativeQuery=true)
	public Customer getCustomer(Long cusId);
}

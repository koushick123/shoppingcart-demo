package com.shoppingcart.anzdemo.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.anzdemo.customer.Customer;
import com.shoppingcart.anzdemo.customer.CustomerRepository;
@Service
public class CustomerServiceImpl implements CustomerRepository{

	Logger logger = Logger.getLogger(CustomerServiceImpl.class.getName());
	@Autowired
	CustomerRepository custRepo;
	@Override
	public Long findCustomer(String email) {
		// TODO Auto-generated method stub
		return custRepo.findCustomer(email);
	}

	@Override
	public void createNewCustomer(Long custId, String email, String name) {
		// TODO Auto-generated method stub
		custRepo.createNewCustomer(custId, email, name);		
	}

	@Override
	public Customer getCustomer(Long cusId) {
		// TODO Auto-generated method stub
		return custRepo.getCustomer(cusId);
	}
	
	@Override
	public List<Customer> findCustomerById(Long id) {
		// TODO Auto-generated method stub
		return custRepo.findCustomerById(id);
	}
}
package com.shoppingcart.anzdemo.controllers;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shoppingcart.anzdemo.customer.Customer;
import com.shoppingcart.anzdemo.customer.CustomerDTO;
import com.shoppingcart.anzdemo.exceptions.CustomerNotFoundException;
import com.shoppingcart.anzdemo.services.CustomerServiceImpl;

@RestController
@RequestMapping("/customer")
@EnableEurekaClient
public class CustomerController {

	Logger logger = Logger.getLogger(CustomerController.class.getName());
	@Autowired
	CustomerServiceImpl custService;
	
	@RequestMapping(value = "/getcustomer/{cusId}", method = RequestMethod.GET, produces={"application/json"})
	@ResponseBody
	public CustomerDTO fetchCustomer(@PathVariable("cusId") long cusId){
		CustomerDTO cusObj = new CustomerDTO();
		List<Customer> customer = (List<Customer>) custService.findCustomerById(cusId);
		logger.info("customer == "+customer);
		if(customer != null && customer.size() > 0 ){
			logger.info("customer size == "+customer.size());
			Customer cust = customer.get(0);
			cusObj.setEmail(cust.getEmail());
			cusObj.setId(cust.getId());
			cusObj.setName(cust.getName());		
		}
		else{
			throw new CustomerNotFoundException(cusId);
		}
		return cusObj;
	}
	/*
	 * This is to test if the API is working.
	 */
	@RequestMapping(value="/show", method=RequestMethod.GET)
	@ResponseBody
	public String getMessage(){
		return "This means API is returning a response";
	}
	
	public Long doesCustomerExist(String email){
		logger.info("Checking if "+email+" exists in system");
		Long cId = custService.findCustomer(email);
		logger.info("Cid = "+cId);
		if(cId == null){
			throw new CustomerNotFoundException(email);
		}
		return cId;
	}
	@RequestMapping(value="/create", method=RequestMethod.POST, consumes={"application/json"})
	@ResponseStatus(HttpStatus.OK)
	public void createCustomer(@RequestBody CustomerDTO newCust){
		try
		{
			doesCustomerExist(newCust.getEmail());
		}
		catch(CustomerNotFoundException notfound){
			logger.info("CustomerNotFoundException == "+notfound.getMessage());
			Long newCustId = Customer.getCustId();
			logger.info("inserting newCustId = "+newCustId);
			custService.createNewCustomer(newCustId, newCust.getEmail(), newCust.getName());
			Long cId = custService.findCustomerById(newCustId).get(0).getId();
			logger.info("AFTER INSERTION Cid = "+cId);
		}
	}
}

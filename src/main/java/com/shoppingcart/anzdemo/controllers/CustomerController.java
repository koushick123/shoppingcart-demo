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
	
	@RequestMapping(value="/isexistsornot/{email}", method=RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String doesCustomerExist(@PathVariable("email") String email){
		logger.info("Checking if "+email+" exists in system");
		Customer cId = custService.findCustomer(email);
		if(cId == null){
			throw new CustomerNotFoundException(email);
		}
		else{
			logger.info("Cid = "+cId.getId());
		}
		return String.valueOf(cId.getId());
	}
	
	@RequestMapping(value="/create", method=RequestMethod.POST, consumes={"application/json"}, produces={"application/json"})
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public CustomerDTO createCustomer(@RequestBody CustomerDTO newCust){
		Long newCustId = Customer.getCustId();
		logger.info("inserting newCustId = "+newCustId);
		custService.createNewCustomer(newCustId, newCust.getEmail(), newCust.getName());
		CustomerDTO createdCust = new CustomerDTO();
		createdCust.setId(newCustId);
		return createdCust;
	}
	
	@RequestMapping(value="/getcustomer/bymail/{email}", method = RequestMethod.GET, produces={"application/json"})
	public CustomerDTO getCustomerByEmail(@PathVariable("email") String mail){
		CustomerDTO custDto = new CustomerDTO();
		Customer customer = custService.findCustomer(mail);
		logger.info("RETRIEVED RECORD = "+customer.getEmail()+" , "+customer.getName()+" , "+customer.getId());
		custDto.setEmail(customer.getEmail());
		custDto.setId(customer.getId());
		custDto.setName(customer.getName());
		return custDto;
	}
}

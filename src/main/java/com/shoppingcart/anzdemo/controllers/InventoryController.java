package com.shoppingcart.anzdemo.controllers;

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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.shoppingcart.anzdemo.customer.CustomerDTO;
import com.shoppingcart.anzdemo.exceptions.InventoryCreateException;
import com.shoppingcart.anzdemo.exceptions.InventoryNotFoundException;
import com.shoppingcart.anzdemo.inventory.Inventory;
import com.shoppingcart.anzdemo.inventory.InventoryDTO;
import com.shoppingcart.anzdemo.services.InventoryServiceImpl;
@RestController
@RequestMapping("/inventory")
@EnableEurekaClient
public class InventoryController {
	Logger logger = Logger.getLogger(InventoryController.class.getName());
	@Autowired
	InventoryServiceImpl invServ;	
	
	@Autowired
	RestTemplate restTempl;
	/*
	 * This method is just to test if Inter service communication is working using Eureka
	 */
	@RequestMapping(value="/getCustInfo/{id}", method=RequestMethod.GET, produces={"application/json"})
	public void getCustomerInfo(@PathVariable("id") String id){
		String result = restTempl.getForObject("http://customer-service/customer/show", String.class);
		logger.info("Fetch from CUSTOMER-SERVICE = "+result);
		try{
			CustomerDTO cust = restTempl.getForObject("http://customer-service/customer/getcustomer/{cusId}",
					CustomerDTO.class, id);
			logger.info("Customer info from CUSTOMER-SERVICE == "+cust.getName());
		}
		catch(RestClientException ex){
			logger.info(ex.getMessage());
		}
	}
	
	@RequestMapping(value="/existsornot/{invId}",method=RequestMethod.GET, produces={"application/json"})
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public InventoryDTO doesInventoryExist(@PathVariable("invId") long invId){
		InventoryDTO inventory = new InventoryDTO();
		Long invID = invServ.doesInventoryExist(new Long(invId));
		logger.info("invID = "+invID);
		if(invID != null){
			Inventory invenDb = invServ.findByNumber(invID);
			inventory.setDescription(invenDb.getDescription());
			inventory.setInvId(invenDb.getInvId());
			inventory.setName(invenDb.getName());
			return inventory;
		}
		else{
			throw new InventoryNotFoundException(String.valueOf(invId));
		}
	}
	
	@RequestMapping(value="/addinventory", method=RequestMethod.POST, consumes={"application/json"})
	@ResponseStatus(HttpStatus.CREATED)
	public void addIventoryItem(@RequestBody InventoryDTO newInven){
		if(newInven.getDescription().trim().equals("") ||
				newInven.getName().trim().equals("")){
				if(newInven.getDescription().trim().equals("")){
					throw new InventoryCreateException("No Inventory description given");
				}
				else{
					throw new InventoryCreateException("No Inventory name given");
				}
		}
		newInven.setInvId(InventoryDTO.getNextInventoryId());
		invServ.addNewInventoryItem(newInven.getInvId(), newInven.getName(), newInven.getDescription());
	}
}

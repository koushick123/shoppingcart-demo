package com.shoppingcart.anzdemo.controllers;

import io.netty.handler.codec.http.HttpHeaders;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.JsonObject;
import com.shoppingcart.anzdemo.checkoutorder.CheckoutOrder;
import com.shoppingcart.anzdemo.checkoutorder.PurchaseDTO;
import com.shoppingcart.anzdemo.customer.CustomerDTO;
import com.shoppingcart.anzdemo.services.CheckoutOrderServiceImpl;

@RestController
@RequestMapping("/checkoutorder")
@EnableEurekaClient
public class CheckoutOrderCustomerController {

	Logger logger = Logger.getLogger(CheckoutOrderCustomerController.class.getName());
		
	@Autowired
	RestTemplate restTempl;
	
	@Autowired
	CheckoutOrderServiceImpl checkoutService;
	
	@RequestMapping(value="/purchase", method=RequestMethod.POST, consumes={"application/json"})
	@ResponseStatus(HttpStatus.CREATED)
	public void makePurchase(@RequestBody PurchaseDTO newPurchase){
		//Check for customer
		Long customerId = null;
		try{
			Long  custId = restTempl.getForObject("http://customer-service/customer/isexistsornot/{email}", 
					Long.class, newPurchase.getEmail());
			logger.info("custId == "+custId);
			customerId = custId;
		}
		catch(RestClientException excep){
			logger.info("Rest client ex = "+excep.getMessage());
			if(excep.getMessage().contains("null")){
				logger.info("Need to create new customer");
				//Create new customer
				CustomerDTO newCust = new CustomerDTO();
				newCust.setId(null);
				newCust.setName(newPurchase.getName());
				newCust.setEmail(newPurchase.getEmail());
				JsonObject custJson = new JsonObject();
				custJson.addProperty("id", "");
				custJson.addProperty("email", newCust.getEmail());
				custJson.addProperty("name", newCust.getName());
				org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				logger.info("Sending JSON = "+custJson.toString());
				HttpEntity<String> reqObj = new HttpEntity<String>(custJson.toString(), headers);
				restTempl.postForObject("http://customer-service/customer/create", reqObj, Long.class);				
				logger.info("Created New Customer. Fetch by mail == "+newCust.getEmail());
				newCust = restTempl.getForObject("http://customer-service/getcustomer/bymail", CustomerDTO.class,
						newPurchase.getEmail());
				logger.info("Created customer = "+newCust.getId());
				customerId = newCust.getId();				
			}
		}
		logger.info("Make purchase for "+customerId);
		if(customerId != null){
			Long newOrderId = CheckoutOrder.getNextCheckoutOrderId();
			checkoutService.doOrderCheckout(newPurchase.getInvId(), customerId, newOrderId);
			CheckoutOrder newOrder = checkoutService.findCheckoutOrderById(newOrderId);
			logger.info("checkout order info === "+newOrder.getCustId()+" , Inv id = "+
			newOrder.getInvId()+" , Order date = "+newOrder.getOrderDate());
		}
	}
}

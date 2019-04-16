package com.shoppingcart.anzdemo.controllers;

import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.shoppingcart.anzdemo.checkoutorder.CheckoutOrder;
import com.shoppingcart.anzdemo.checkoutorder.PurchaseDTO;
import com.shoppingcart.anzdemo.customer.CustomerDTO;
import com.shoppingcart.anzdemo.exceptions.PurchaseInCompleteException;
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
			Long custId = Long.valueOf(restTempl.getForObject("http://customer-service/customer/isexistsornot/{email}", 
					String.class, newPurchase.getEmail()));
			logger.info("custId == "+custId);
			customerId = custId;
		}
		catch(RestClientException excep){
			logger.info("Rest client ex = "+excep.getMessage());
			if(excep.getMessage().contains("null")){
				logger.info("Need to create new customer");
				//Create new customer
				JSONObject custJson = new JSONObject();
				try {
					custJson.put("email", newPurchase.getEmail());
					custJson.put("name", newPurchase.getName());
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// set headers
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> entity = new HttpEntity<String>(custJson.toString(), headers);
				logger.info("Sending JSON = "+custJson.toString());
				ResponseEntity<CustomerDTO> custResp = restTempl.exchange("http://customer-service/customer/create", 
						HttpMethod.POST, entity, CustomerDTO.class);
				if (custResp.getStatusCode() != HttpStatus.CREATED) {
					throw new PurchaseInCompleteException(newPurchase.getInvId(), -1L, 
							"Unable to Create new Customer");
				}				
				CustomerDTO newCust2 = restTempl.getForObject("http://customer-service/customer/getcustomer/bymail/{email}", CustomerDTO.class,
						newPurchase.getEmail());
				logger.info("Created customer = "+newCust2.getId());
				customerId = newCust2.getId();
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

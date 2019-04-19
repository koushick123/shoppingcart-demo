package com.shoppingcart.anzdemo.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.shoppingcart.anzdemo.checkoutorder.CheckoutOrder;
import com.shoppingcart.anzdemo.checkoutorder.CheckoutOrderDTO;
import com.shoppingcart.anzdemo.checkoutorder.PurchaseDTO;
import com.shoppingcart.anzdemo.customer.CustomerDTO;
import com.shoppingcart.anzdemo.exceptions.CustomerNotFoundException;
import com.shoppingcart.anzdemo.exceptions.PurchaseInCompleteException;
import com.shoppingcart.anzdemo.inventory.InventoryDTO;
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
		Long inventoryId = null;
		//Check for existing customer
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
				try {
					CustomerDTO newCust2 = restTempl.getForObject("http://customer-service/customer/getcustomer/bymail/{email}", 
							CustomerDTO.class,
							newPurchase.getEmail());
					logger.info("Created customer = "+newCust2.getId());
					customerId = newCust2.getId();
				}
				catch(RestClientException restEx) {
					if(restEx.getMessage().contains("ConnectException")) {
						throw new ServiceUnavailableException("Customer Service");
					}
				}
			}
			else if(excep.getMessage().contains("ConnectException")) {
				throw new ServiceUnavailableException("Customer Service");
			}
		}
		//Check for inventory
		try{
			InventoryDTO inven = restTempl.getForObject("http://inventory-service/inventory/existsornot/{invId}", InventoryDTO.class,
					newPurchase.getInvId());
			logger.info("inven Id = "+inven.getInvId());
			inventoryId = inven.getInvId();
		}
		catch(RestClientException ex){
			logger.info("INVEN Rest Exception == "+ex.getMessage());
			if(ex.getMessage().contains("null")) {
				throw new PurchaseInCompleteException(newPurchase.getInvId(), customerId, 
					"Unable to find Inventory");
			}
			else if(ex.getMessage().contains("ConnectException")) {
				throw new ServiceUnavailableException("Inventory Service");
			}
		}
		logger.info("MAKE PURCHASE for "+customerId+", with inventory "+inventoryId);
		if(customerId != null && inventoryId != null){
			Long newOrderId = CheckoutOrder.getNextCheckoutOrderId();
			checkoutService.doOrderCheckout(inventoryId, customerId, newOrderId);
			CheckoutOrder newOrder = checkoutService.findCheckoutOrderById(newOrderId);
			logger.info("checkout order info === "+newOrder.getCustId()+" , Inv id = "+
			newOrder.getInvId()+" , Order date = "+newOrder.getOrderDate());
		}
	}
	
	@RequestMapping(value="/getOrderInfoForCust/{custId}", method=RequestMethod.GET, produces= {"application/json"})
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public List<CheckoutOrderDTO> getOrderInfoForCust(@PathVariable("custId") Long custId) {
		List<CheckoutOrderDTO> checkoutOrder = new ArrayList<CheckoutOrderDTO>();
		List<CheckoutOrder> checkoutAll = checkoutService.findAllCheckoutOrders();
		try {
			restTempl.getForObject("http://customer-service/customer/getcustomer/{cusId}", CustomerDTO.class, custId.longValue());
			logger.info("Customer "+custId+" exists");
		}
		catch(RestClientException restExcep) {
			logger.info("EXCEPTION = "+restExcep.getMessage());
			if(restExcep.getMessage().contains("null")) {
				throw new CustomerNotFoundException(custId);
			}
			else if(restExcep.getMessage().contains("ConnectException")) {
				throw new ServiceUnavailableException("Customer Service");
			}
			else {
				throw restExcep;
			}
		}
		Stream<CheckoutOrder> checkoutForCust = checkoutAll.stream().filter(p -> p.getCustId().longValue() == custId.longValue());
		checkoutForCust.forEach(order -> {
			CheckoutOrderDTO checkout = new CheckoutOrderDTO();
			System.out.println("FETCHING order id == "+order.getOrderId());
			checkout.setCustId(order.getCustId());
			checkout.setInvId(order.getInvId());
			checkout.setOrderDate(order.getOrderDate());
			checkout.setOrderId(order.getOrderId());
			checkoutOrder.add(checkout);
		});
		return checkoutOrder;
	}
}

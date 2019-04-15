package com.shoppingcart.anzdemo.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppingcart.anzdemo.checkoutorder.CheckoutOrder;
import com.shoppingcart.anzdemo.checkoutorder.CheckoutOrderRepository;
@Service
public class CheckoutOrderServiceImpl implements CheckoutOrderRepository{

	Logger logger = Logger.getLogger(CheckoutOrderServiceImpl.class.getName());
	@Autowired
	CheckoutOrderRepository checkoutOrderRepo;
	
	@Override
	public void doOrderCheckout(Long invId, Long custId, Long orderId) {
		// TODO Auto-generated method stub
		checkoutOrderRepo.doOrderCheckout(invId, custId, orderId);
	}

	@Override
	public CheckoutOrder findCheckoutOrderById(Long orderId) {
		// TODO Auto-generated method stub
		return checkoutOrderRepo.findCheckoutOrderById(orderId);
	}
}
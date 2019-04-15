package com.shoppingcart.anzdemo.services;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.shoppingcart.anzdemo.checkoutorder.CheckoutOrder;
import com.shoppingcart.anzdemo.checkoutorder.CheckoutOrderRepository;
import com.shoppingcart.anzdemo.controllers.CheckoutOrderCustomerController;

@ComponentScan(basePackageClasses = {CheckoutOrderCustomerController.class, CheckoutOrder.class, CheckoutOrderRepository.class,
		CheckoutOrderServiceImpl.class})
@EntityScan({"com.shoppingcart.anzdemo.checkoutorder"})
@EnableJpaRepositories({"com.shoppingcart.anzdemo.checkoutorder"})
@SpringBootApplication
public class CheckoutOrderServer {
	
	Logger logger = Logger.getLogger(getClass().getName());
	
	public static void main(String[] args) {
		// Tell server to look for checkoutorder-server.yml for config info
		System.setProperty("spring.config.name", "checkoutorder-server");

		SpringApplication.run(CheckoutOrderServer.class, args);
	}
}

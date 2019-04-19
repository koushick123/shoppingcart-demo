package com.shoppingcart.anzdemo.services;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.shoppingcart.anzdemo.controllers.CustomerController;
import com.shoppingcart.anzdemo.customer.Customer;
import com.shoppingcart.anzdemo.customer.CustomerRepository;

@Configuration
@ComponentScan(basePackageClasses = {CustomerController.class, Customer.class, CustomerRepository.class,
		CustomerServiceImpl.class})
@EntityScan({"com.shoppingcart.anzdemo.customer"})
@EnableJpaRepositories({"com.shoppingcart.anzdemo.customer"})
@PropertySource("classpath:db-config.properties")
@SpringBootApplication
public class CustomerServer {
	
	Logger logger = Logger.getLogger(getClass().getName());
	
	public static void main(String[] args) {
		// Tell server to look for customer-server.yml for config info
		System.setProperty("spring.config.name", "customer-server");

		SpringApplication.run(CustomerServer.class, args);
	}
}

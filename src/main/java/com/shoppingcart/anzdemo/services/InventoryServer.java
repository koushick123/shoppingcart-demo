package com.shoppingcart.anzdemo.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.shoppingcart.anzdemo.controllers.InventoryController;
import com.shoppingcart.anzdemo.inventory.Inventory;
import com.shoppingcart.anzdemo.inventory.InventoryRepository;

@Configuration
@ComponentScan(basePackageClasses = {InventoryController.class, Inventory.class, InventoryRepository.class,
		InventoryServiceImpl.class})
@SpringBootApplication
@EntityScan({"com.shoppingcart.anzdemo.inventory"})
@EnableJpaRepositories({"com.shoppingcart.anzdemo.inventory"})
public class InventoryServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("spring.config.name", "inventory-server");

		SpringApplication.run(InventoryServer.class, args);
	}
}

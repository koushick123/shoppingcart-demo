package com.shoppingcart.anzdemo.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.ComponentScan;
@SpringBootApplication
@EnableEurekaServer
@ComponentScan(basePackageClasses={EurekaRegistrationServer.class})
public class EurekaRegistrationServer {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("spring.config.name", "eureka-registration-server");

		SpringApplication.run(EurekaRegistrationServer.class, args);
	}
}

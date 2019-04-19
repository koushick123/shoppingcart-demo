package com.shoppingcart.anzdemo.services;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.web.client.RestTemplate;
@SpringBootApplication
@EnableEurekaServer
public class EurekaRegistrationServer {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("spring.config.name", "eureka-registration-server");

		SpringApplication.run(EurekaRegistrationServer.class, args);
	}
	
	@LoadBalanced
	@Bean
	public RestTemplate restTempl() {
        return new RestTemplate();
    }
	
	@Bean
	public static DataSource dataSource() {
		
		// Create an in-memory H2 relational database containing some demo
		// accounts.
		DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:testdb/schema.sql")
				.addScript("classpath:testdb/data.sql").build();

		// Sanity check
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT cust_id FROM T_CUSTOMER");
		users.forEach(item -> {
			item.forEach((str, obj) -> {
				System.out.println("Str = "+str+" , obj = "+obj);
			});
		});
		return dataSource;
	}
}

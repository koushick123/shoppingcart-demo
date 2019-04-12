package com.shoppingcart.anzdemo.services;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import com.shoppingcart.anzdemo.controllers.CustomerController;
import com.shoppingcart.anzdemo.customer.Customer;
import com.shoppingcart.anzdemo.customer.CustomerRepository;

@Configuration
@ComponentScan(basePackageClasses = {CustomerController.class, Customer.class, CustomerRepository.class,
		CustomerServiceImpl.class})
/*@ComponentScan(basePackages  = {
		"com.shoppingcart.anzdemo"
})*/
@EntityScan({"com.shoppingcart.anzdemo.customer"})
@EnableJpaRepositories({"com.shoppingcart.anzdemo.customer"})
@PropertySource("classpath:db-config.properties")
@SpringBootApplication
public class CustomerServer {
	
	Logger logger = Logger.getLogger(getClass().getName());
	@Bean
	public static DataSource dataSource() {
		System.out.println("CustomerServer dataSource() invoked");

		// Create an in-memory H2 relational database containing some demo
		// accounts.
		DataSource dataSource = (new EmbeddedDatabaseBuilder()).addScript("classpath:testdb/schema.sql")
				.addScript("classpath:testdb/data.sql").build();

		System.out.println("CustomerServer dataSource = " + dataSource);

		// Sanity check
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Map<String, Object>> users = jdbcTemplate.queryForList("SELECT cust_id FROM T_CUSTOMER");
		System.out.println("System has " + users.size() + " users");

		return dataSource;
	}
	public static void main(String[] args) {
		// Tell server to look for customer-server.yml for config info
		System.setProperty("spring.config.name", "customer-server");

		SpringApplication.run(CustomerServer.class, args);
	}
}

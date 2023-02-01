package com.mongo.inventoryservice;

import com.mongo.inventoryservice.model.Inventory;
import com.mongo.inventoryservice.repo.InventoryRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepo inventoryRepo){
		return args -> {
			Inventory inventory = new Inventory();

			inventory.setSkuCode("iphone_13");
			inventory.setQuantity(1000);

			Inventory inventory1 = new Inventory();

			inventory1.setSkuCode("iphone_13_red");
			inventory1.setQuantity(0);

			inventoryRepo.save(inventory1);
			inventoryRepo.save(inventory);


		};
	}

}

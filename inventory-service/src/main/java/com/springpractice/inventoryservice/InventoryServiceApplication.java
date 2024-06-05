package com.springpractice.inventoryservice;

//import com.springpractice.inventoryservice.model.Inventory;
//import com.springpractice.inventoryservice.repository.InventoryRepository;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
//        return args -> {
//            Inventory inventory = new Inventory();
//            Inventory inventory1 = new Inventory();
//
//            inventory1.setSkuCode("lombshoes");
//            inventory1.setQuantity(10);
//
//            inventory.setSkuCode("combshoes");
//            inventory.setQuantity(0);
//
//            inventoryRepository.save(inventory);
//            inventoryRepository.save(inventory1);
//        };
//    }
}

package com.example.webshop.utility;

import com.example.webshop.models.Customer;
import com.example.webshop.models.Item;
import com.example.webshop.models.Order;
import com.example.webshop.repositories.CustomerRepository;
import com.example.webshop.repositories.ItemRepository;
import com.example.webshop.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class ConfigStart {

    @Value("${config.start:false}")
    private boolean configStart;

    private static final Logger logger = LoggerFactory.getLogger(ConfigStart.class);

    @Bean
    public CommandLineRunner demo(CustomerRepository customerRepo,
                                  ItemRepository itemRepository,
                                  OrderRepository orderRepository) {
        return (args) -> {
            logger.info("ConfigStart: " + configStart);
            if(configStart) {

                logger.info("Running ConfigStart: adding data");

                Customer customer = new Customer("Gollum", "Smeagol", "1234567890");
                Customer customer1 = new Customer("Frodo", "Baggins", "0987654321");
                Customer customer2 = new Customer("Samwise", "Gamgee", "1234509876");
                Customer customer3 = new Customer("Bilbo", "Baggins", "6789054321");

                customerRepo.save(customer);
                customerRepo.save(customer1);
                customerRepo.save(customer2);
                customerRepo.save(customer3);

                Item item = new Item("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops", 109.95);
                Item item1 = new Item("Mens Casual Premium Slim Fit T-Shirts ", 22.3);
                Item item2 = new Item("Mens Cotton Jacket", 55.99);
                Item item3 = new Item("Mens Casual Slim Fit", 15.99);
                Item item4 = new Item( "WD 2TB Elements Portable External Hard Drive - USB 3.0", 64.0);

                itemRepository.save(item);
                itemRepository.save(item1);
                itemRepository.save(item2);
                itemRepository.save(item3);
                itemRepository.save(item4);

                orderRepository.save(new Order(customer, List.of(item, item1, item2), LocalDateTime.from(LocalDateTime.of(2023, 3, 25, 21, 30, 42, 0))));
                orderRepository.save(new Order(customer1, List.of(item3, item4), LocalDateTime.from(LocalDateTime.of(2023, 4, 1, 9, 39, 42, 0))));
                orderRepository.save(new Order(customer2, List.of(item, item2, item4),  LocalDateTime.from(LocalDateTime.of(2023, 4, 10, 16, 30, 30, 0))));
                orderRepository.save(new Order(customer3, List.of(item1, item3), LocalDateTime.from(LocalDateTime.of(2023, 4, 20, 12, 10, 20, 0))));

            }
        };
    }
}
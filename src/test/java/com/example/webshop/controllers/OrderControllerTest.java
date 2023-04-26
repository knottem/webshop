package com.example.webshop.controllers;

import com.example.webshop.models.Customer;
import com.example.webshop.models.Item;
import com.example.webshop.models.Order;
import com.example.webshop.repositories.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository mockRepo;

    Order order = new Order(
            new Customer(),
            List.of(
                    new Item(),
                    new Item())
    );

    Order order1 = new Order(
            new Customer(),
            List.of(
                    new Item())
    );

    Order order2 = new Order(
            new Customer(),
            List.of(
                    new Item(),
                    new Item(),
                    new Item())
    );

    List<Order> orderList = List.of(order, order1, order2);

    @BeforeEach
    void setUp() {

        when(mockRepo.findAll()).thenReturn(orderList);
        when(mockRepo.findById(1L)).thenReturn(Optional.of(order));
        when(mockRepo.findById(2L)).thenReturn(Optional.of(order1));
        when(mockRepo.findById(3L)).thenReturn(Optional.of(order2));

    }

    @Test
    void getAllProducts() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        new ObjectMapper()
                                .writeValueAsString(orderList)
                ));
    }

    @Test
    void getProductById() throws Exception {
        mockMvc.perform(get("/orders/2"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        new ObjectMapper()
                                .writeValueAsString(order1)
                ));
    }
}
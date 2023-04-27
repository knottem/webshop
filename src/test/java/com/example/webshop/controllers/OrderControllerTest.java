package com.example.webshop.controllers;

import com.example.webshop.models.Customer;
import com.example.webshop.models.Item;
import com.example.webshop.models.Order;
import com.example.webshop.repositories.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
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

    ObjectMapper mapper = JsonMapper.builder()
                .findAndAddModules()
                .build();

    Order order = new Order(
            new Customer("testFirstName1", "testLastName1", "123123123"),
            List.of(
                    new Item("testItem", 200),
                    new Item("testItem2", 300))
    );

    Order order1 = new Order(
            new Customer("testFirstName2", "testLastName2", "123123123"),
            List.of(
                    new Item("testItem", 200)
    ));

    Order order2 = new Order(
            new Customer("testFirstName3", "testLastName3", "123123123"),
            List.of(
                    new Item("testItem", 300),
                    new Item("testItem2", 400),
                    new Item("testItem3", 500))
    );

    List<Order> orderList = new ArrayList<>(List.of(order, order1, order2));

    @BeforeEach
    void setUp() {

        when(mockRepo.findById(1L)).thenReturn(Optional.of(order));
        when(mockRepo.findById(2L)).thenReturn(Optional.of(order1));
        when(mockRepo.findById(3L)).thenReturn(Optional.of(order2));
        when(mockRepo.findAll()).thenReturn(orderList);

    }

    @Test
    void testGetAllOrders() throws Exception {
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                     mapper.writeValueAsString(orderList)
                ));
    }

    @Test
    void testGetOrderById() throws Exception {
        mockMvc.perform(get("/orders/2"))
                .andExpect(status().isOk())
                    .andExpect(content().json(
                        mapper.writeValueAsString(order1)
                ));
    }

    @Test
    void testGetOrderByIdFaulty() throws Exception {
        mockMvc.perform(get("/orders/4"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"error\":\"Order not found\"}"));
    }

}
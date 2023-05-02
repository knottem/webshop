package com.example.webshop.controllers;

import com.example.webshop.models.Customer;
import com.example.webshop.models.Item;
import com.example.webshop.models.Order;
import com.example.webshop.repositories.ItemRepository;
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
class itemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemRepository mockRepo;

    ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    Item item1 = new Item("testItem1", 200);

    Item item2 = new Item("testItem2", 300);

    Item item3 = new Item("testItem3", 400);

    List<Item> itemList = new ArrayList<>(List.of(item1, item2, item3));

    @BeforeEach
    void setUp() {
        when(mockRepo.findById(1L)).thenReturn(Optional.of(item1));
        when(mockRepo.findById(2L)).thenReturn(Optional.of(item2));
        when(mockRepo.findById(3L)).thenReturn(Optional.of(item3));
        when(mockRepo.findAll()).thenReturn(itemList);
    }

    @Test
    void testGetAllItems() throws Exception {
        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        mapper.writeValueAsString(itemList)
                ));
    }

    @Test
    void testGetItemById() throws Exception {
        mockMvc.perform(get("/items/2"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        mapper.writeValueAsString(item2)
                ));
    }

    @Test
    void testGetItemByIdFaulty() throws Exception {
        mockMvc.perform(get("/items/4"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"error\":\"Item not found\"}"));
    }

}
package com.example.webshop.controllers;

import com.example.webshop.models.Customer;
import com.example.webshop.repositories.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository mockRepo;

    ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    Customer customer1 = new Customer(
            "testFirstName1",
            "testLastName1",
            "testSocialSecurityNumber1"
    );

    Customer customer2 = new Customer(
            "testFirstName2",
            "testLastName2",
            "testSocialSecurityNumber2"
    );

    List<Customer> customerList = new ArrayList<>(List.of(customer1, customer2));

    @BeforeEach
    void setUp() {
        when(mockRepo.findById(1L)).thenReturn(Optional.of(customer1));
        when(mockRepo.findById(2L)).thenReturn(Optional.of(customer2));
        when(mockRepo.findAll()).thenReturn(customerList);
    }

    @Test
    void testGetAllCustomers() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        mapper.writeValueAsString(customerList)
                ));
    }

    @Test
    void testGetCustomerById() throws Exception {
        mockMvc.perform(get("/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        mapper.writeValueAsString(customer1)
                ));
    }

    @Test
    void testGetCustomerByIdFaulty() throws Exception {
        mockMvc.perform(get("/customers/3"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"error\":\"Customer not found\"}"));
    }

    @Test
    void testAddCustomer() throws Exception {
        Customer customer3 = new Customer(
                "testFirstName3",
                "testLastName3",
                "testSocialSecurityNumber3"
        );
        String content = mapper.writeValueAsString(customer3);

        when(mockRepo.save(any(Customer.class))).thenReturn(customer3);


        mockMvc.perform(post("/customers/").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(customer3)));
    }
}

package com.example.webshop.controllers;

import com.example.webshop.models.Customer;
import com.example.webshop.repositories.CustomerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(webController.class)
class webControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
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

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer2));
        when(customerRepository.findAll()).thenReturn(List.of(customer1, customer2));
    }


    @Test
    void testForm() throws Exception {
        mockMvc.perform(get("/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerForm"));
    }


    @Test
    void testFormPost() throws Exception {
        mockMvc.perform(post("/form")
                .param("fName", "testFirstName3")
                .param("lName", "testLastName3")
                .param("ssn", "testSocialSecurityNumber3"))
                .andExpect(status().isOk())
                .andExpect(view().name("thanks"))
                .andExpect(model().attributeExists("fname"))
                .andExpect(model().attribute("fname", "testFirstName3"))
                .andExpect(model().attributeExists("lname"))
                .andExpect(model().attribute("lname", "testLastName3"));

        verify(customerRepository, times(1)).save(new Customer(
                "testFirstName3",
                "testLastName3",
                "testSocialSecurityNumber3"
        ));
    }

    @Test
    void testList() throws Exception {
        mockMvc.perform(get("/customerList"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerList"))
                .andExpect(model().attribute("customers", List.of(
                        new Customer(
                                "testFirstName1",
                                "testLastName1",
                                "testSocialSecurityNumber1"
                        ),
                        new Customer(
                                "testFirstName2",
                                "testLastName2",
                                "testSocialSecurityNumber2"
                        )
                )));

    }

    @Test
    void testGetCustomerById() throws Exception {
        mockMvc.perform(get("/customerList/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerId"))
                .andExpect(model().attribute("customer", new Customer(
                        "testFirstName1",
                        "testLastName1",
                        "testSocialSecurityNumber1"
                )));
    }

    @Test
    void testGetCustomerByIdNotFound() throws Exception {
        mockMvc.perform(get("/customerList/3"))
                .andExpect(status().isOk())
                .andExpect(view().name("customerList"))
                .andExpect(model().attribute("error", "Customer not found"));
    }

    @Test
    void testDeleteCustomerById() throws Exception {
        mockMvc.perform(get("/customerList/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customerList"));

        verify(customerRepository, times(1)).deleteById(1L);
    }

}
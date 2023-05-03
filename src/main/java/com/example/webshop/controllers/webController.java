package com.example.webshop.controllers;

import com.example.webshop.models.Customer;
import com.example.webshop.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class webController {

    private final Logger logger = LoggerFactory.getLogger(webController.class);

    private final CustomerRepository customerRepository;

    public webController(CustomerRepository repo) {
        customerRepository = repo;
    }

    @GetMapping("/form")
    public String form() {
        return "customerForm";
    }

    @PostMapping("/form")
    public String formPost(@RequestParam String fName,
                               @RequestParam String lName,
                               @RequestParam String ssn,
                               Model model) {
        if(fName.isEmpty() || lName.isEmpty() || ssn.isEmpty()){
            model.addAttribute("error", "Please fill in all fields");
            return "customerForm";
        }
        customerRepository.save(new Customer(fName, lName, ssn));
        logger.info("Adding name: " + fName + " " + lName + " " + ssn);
        model.addAttribute("fname", fName);
        model.addAttribute("lname", lName);
        return "thanks";
    }

    @GetMapping("/customerList")
    public String list(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "customerList";
    }

    @GetMapping("/customerList/{id}")
    public String getCustomerById(@PathVariable long id, Model model) {
        Customer customer = customerRepository.findById(id).isPresent() ? customerRepository.findById(id).get() : null;
        if (customer == null) {
            model.addAttribute("customers", customerRepository.findAll());
            model.addAttribute("error", "Customer not found");
            return "customerList";
        } else {
            model.addAttribute("customer", customer);
            return "customerId";
        }
    }

    @GetMapping("/customerList/{id}/delete")
    public String deleteCustomerById(@PathVariable long id){
        customerRepository.deleteById(id);
        return "redirect:/customerList";
    }

}

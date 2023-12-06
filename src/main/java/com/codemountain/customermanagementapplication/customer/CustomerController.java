package com.codemountain.customermanagementapplication.customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value= "/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    List<Customer> getAllCustomers() {
       return customerService.getAllCustomers();
    }

    @GetMapping(path = "{id}")
    Customer getCustomerById(@PathVariable(name = "id") Long customerId) {
        return customerService.getCustomerById(customerId);
    }
}

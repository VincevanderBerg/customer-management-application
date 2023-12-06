package com.codemountain.customermanagementapplication.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Long customerId);
    void insertCustomerIntoCustomers(Customer customer);
}

package com.codemountain.customermanagementapplication.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Long customerId);
    boolean existsCustomerWithId(Long customerId);
    boolean existsCustomerWithEmail(String customerEmail);
    void insertCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomerWithId(Long customerId);
}

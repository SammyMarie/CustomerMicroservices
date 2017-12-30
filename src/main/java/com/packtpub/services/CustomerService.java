package com.packtpub.services;

import com.packtpub.model.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);
    Customer updateCustomer(Customer customer);

    Customer findCustomer(String customerId);
    Customer findCustomerByNames(String firstName, String lastName);
    //List<Customer> findCustomerTypeByNames(String firstName, String lastName);
    List<Customer> findCustomers();
}
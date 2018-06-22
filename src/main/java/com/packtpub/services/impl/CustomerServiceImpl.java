package com.packtpub.services.impl;

import com.packtpub.model.Customer;
import com.packtpub.repository.CustomerRepository;
import com.packtpub.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    public void createCustomer(Customer customer) {
        repository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Customer findCustomer(String customerId) {
        return repository.findOne(customerId);
    }

    @Override
    public Customer findCustomerByNames(String firstName, String lastName) {
        return repository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<Customer> findCustomers() {
        return repository.findAll();
    }
}
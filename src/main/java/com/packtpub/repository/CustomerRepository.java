package com.packtpub.repository;

import com.packtpub.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

    Customer findByFirstNameAndLastName(String firstName, String lastName);
    //List<Customer> findByFirstNameAndLastName(String firstName, String lastName);
}
package com.packtpub.api.controller;

import com.packtpub.api.component.CustomerReservationFallback;
import com.packtpub.contract.Reservation;
import com.packtpub.contract.ReservationClient;
import com.packtpub.model.Customer;
import com.packtpub.services.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RefreshScope
@RestController
public class CustomerController {

    private final CustomerService service;
    private final CustomerReservationFallback fallback;
    private final ReservationClient reservationClient;

    @Autowired
    public CustomerController(CustomerService service, CustomerReservationFallback fallback, ReservationClient reservationClient) {
        this.service = service;
        this.fallback = fallback;
        this.reservationClient = reservationClient;
    }

    @GetMapping(value = "/customers")
    public ResponseEntity<List<Customer>> findAllCustomers(){

        log.info("Finding all Customers");

        List<Customer> customers = service.findCustomers();

        if(CollectionUtils.isNotEmpty(customers)){
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/customers/{customerId}")
    public ResponseEntity<Customer> findCustomer(@PathVariable("customerId") String customerId){

        log.info(String.format("Finding Customer with Id %s", customerId));

        Customer customer = service.findCustomer(customerId);

        List<Reservation> reservations = retrieveReservationsList(customer);

        fallback.populateReserveMap(customerId, reservations);

        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_JSON)
                             .body(customer);
    }

    @GetMapping(value = "/customers/names")
    public ResponseEntity<Customer> findCustomerByNames(@RequestParam Map<String, String> customerNames){

        String firstName = customerNames.get("firstName");
        String lastName = customerNames.get("lastName");

        log.info(String.format("Finding Customer with firstName %s and lastName %s", firstName, lastName));

        Customer customer = service.findCustomerByNames(firstName, lastName);

        String customerId = customer.getCustomerId();
        List<Reservation> reservations = reservationClient.findCustomerReservations(customerId);
        customer.setReservations(reservations);

        if(customer == null){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok()
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .body(customer);
        }
    }

    @PostMapping(value = "/customers")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer){

        log.info(String.format("Saving Customer %s", customer));

        service.createCustomer(customer);

        return ResponseEntity.status(201).build();
    }

    @PutMapping(value = "/customers/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable String customerId, @RequestBody Customer customer){

        Customer customerSearch = service.findCustomer(customerId);


        if(customerSearch != null && customerId.equals(customer.getCustomerId())){

            log.info(String.format("Updating Customer %s", customer));

            service.updateCustomer(customer);
            return ResponseEntity.accepted().build();
        }else {

            return ResponseEntity.status(409).body("{customerId requested and current Id don't match!}");
        }
    }

    private List<Reservation> retrieveReservationsList(Customer customer) {

        String customerId = customer.getCustomerId();

        List<Reservation> reservations = reservationClient.findCustomerReservations(customerId);

        if(CollectionUtils.isNotEmpty(reservations)){

            customer.setReservations(reservations);
        }

        return reservations;
    }
}
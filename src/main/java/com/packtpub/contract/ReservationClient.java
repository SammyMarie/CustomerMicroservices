package com.packtpub.contract;

import com.packtpub.api.component.CustomerReservationFallback;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Primary
@FeignClient(name = "reservation-services", fallback = CustomerReservationFallback.class)
public interface ReservationClient {

    @GetMapping(value = "/reservations/customers/{customerId}")
    List<Reservation> findCustomerReservations(@PathVariable("customerId") String customerId);

    @GetMapping(value = "/reservations/customers/names/{customerId}")
    Map<String, List<Reservation>> findCustomerAllReservations(@PathVariable("customerId") String customerId);
}
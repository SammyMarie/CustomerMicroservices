package com.packtpub.api.component;

import com.packtpub.contract.Reservation;
import com.packtpub.contract.ReservationClient;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CustomerReservationFallback implements ReservationClient{

    private Map<String, List<Reservation>> reservationMap = new HashMap<>();

    public void populateReserveMap(String customerId, List<Reservation> reservations) {

        if(CollectionUtils.isNotEmpty(reservations)){

            reservations.forEach(reserve -> reservationMap.put(customerId, reservations));
        }
    }

    @Override
    public List<Reservation> findCustomerReservations(String customerId) {

        List<Reservation> reservationList = Collections.emptyList();

        if(reservationMap.containsKey(customerId)){
            reservationList = reservationMap.get(customerId);
        }

        return reservationList;
    }

    @Override
    public Map<String, List<Reservation>> findCustomerAllReservations(String customerId) {
        return null;
    }
}
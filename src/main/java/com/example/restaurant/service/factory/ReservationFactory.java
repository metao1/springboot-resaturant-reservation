package com.example.restaurant.service.factory;

import com.example.restaurant.repository.model.ReservationEntity;
import com.example.restaurant.repository.model.TableLocationType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ReservationFactory {
    public static ReservationEntity createReservation(TableLocationType locationType,
                                                      int guestNumber,
                                                      LocalDate reservationDate,
                                                      LocalDateTime arrivalTime,
                                                      LocalDateTime departureTime) {
        ReservationEntity reservation = new ReservationEntity();
        reservation.setTrackingId(UUID.randomUUID().toString());
        reservation.setLocationType(locationType);
        reservation.setGuestNumber(guestNumber);
        reservation.setReservationDate(reservationDate);
        reservation.setArrivalTime(arrivalTime);
        reservation.setDepartureTime(departureTime);
        return reservation;
    }
}

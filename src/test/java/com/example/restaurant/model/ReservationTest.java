package com.example.restaurant.model;

import org.junit.jupiter.api.Test;

import static com.example.restaurant.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationTest {

    @Test
    public void testOverlapsWithOverlappingReservations() {
        // given
        var locationType = TableLocationType.INDOOR_WINDOW;
        ReservationEntity reservation1 = new ReservationEntity();
        reservation1.setLocationType(locationType);
        reservation1.setGuestNumber(GUEST_NUMBER);
        reservation1.setReservationDate(RESERVATION_DATE);
        reservation1.setArrivalTime(ARRIVAL_TIME);
        reservation1.setDepartureTime(DEPARTURE_TIME);

        ReservationEntity reservation2 = new ReservationEntity();
        reservation2.setLocationType(locationType);
        reservation2.setGuestNumber(GUEST_NUMBER);
        reservation2.setReservationDate(RESERVATION_DATE);
        reservation2.setArrivalTime(ARRIVAL_TIME.plusHours(1));
        reservation2.setDepartureTime(DEPARTURE_TIME.plusHours(1));

        // when
        boolean result = reservation1.overlapsWith(reservation2);

        // then
        assertTrue(result);
    }

    @Test
    public void testOverlapsWithNonOverlappingReservations() {
        // given
        ReservationEntity reservation1 = new ReservationEntity();
        reservation1.setReservationDate(RESERVATION_DATE);
        reservation1.setArrivalTime(ARRIVAL_TIME);
        reservation1.setDepartureTime(DEPARTURE_TIME);

        ReservationEntity reservation2 = new ReservationEntity();
        reservation2.setReservationDate(RESERVATION_DATE);
        reservation2.setArrivalTime(ARRIVAL_TIME.plusHours(2));
        reservation2.setDepartureTime(DEPARTURE_TIME.plusHours(2));

        // when
        boolean result = reservation1.overlapsWith(reservation2);

        // then
        assertFalse(result);
    }

    @Test
    public void testOverlapsWithNullReservation() {
        //given
        ReservationEntity reservation1 = new ReservationEntity();
        reservation1.setGuestNumber(GUEST_NUMBER);
        reservation1.setReservationDate(RESERVATION_DATE);
        reservation1.setArrivalTime(ARRIVAL_TIME);
        reservation1.setDepartureTime(DEPARTURE_TIME);

        // when
        boolean result = reservation1.overlapsWith(null);

        // then
        assertFalse(result);
    }
}
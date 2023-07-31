package com.example.restaurant.service.factory;

import com.example.restaurant.repository.model.ReservationEntity;
import com.example.restaurant.repository.model.TableLocationType;
import com.example.restaurant.util.TestUtil;
import org.junit.jupiter.api.Test;

import static com.example.restaurant.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReservationFactoryTest {

    @Test
    public void testCreateReservation() {
        // given
        var locationType = TableLocationType.INDOOR_ROOM;
        var table = TestUtil.createMockedTable(locationType);
        // when
        ReservationEntity reservation = TestUtil.createMockedReservation(locationType, GUEST_NUMBER, table);
        table.addReservation(reservation);

        // Then
        assertEquals(table, reservation.getTable());
        assertNotNull(reservation.getTrackingId());
        assertEquals(locationType, reservation.getLocationType());
        assertEquals(GUEST_NUMBER, reservation.getGuestNumber());
        assertEquals(RESERVATION_DATE, reservation.getReservationDate());
        assertEquals(ARRIVAL_TIME, reservation.getArrivalTime());
        assertEquals(DEPARTURE_TIME, reservation.getDepartureTime());
    }
}
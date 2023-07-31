package com.example.restaurant.util;

import com.example.restaurant.model.ReservationEntity;
import com.example.restaurant.model.TableEntity;
import com.example.restaurant.model.TableLocationType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import java.io.IOException;

import static com.example.restaurant.util.TestConstants.*;

@UtilityClass
public class TestUtil {

    public static ReservationEntity createMockedReservation(TableLocationType locationType, int guestNumber, TableEntity table) {
        // Create a Reservation object with a different table location type
        ReservationEntity reservation = new ReservationEntity();
        reservation.setTrackingId(TRACKING_ID);
        reservation.setTable(table);
        reservation.setLocationType(locationType);
        reservation.setGuestNumber(guestNumber);
        reservation.setReservationDate(RESERVATION_DATE);
        reservation.setArrivalTime(ARRIVAL_TIME);
        reservation.setDepartureTime(DEPARTURE_TIME);

        return reservation;
    }

    /**
     * Convert an object to JSON byte array.
     *
     * @param object the object to convert.
     * @return the JSON byte array.
     */
    public static byte[] convertObjectToJsonBytes(ObjectMapper mapper, Object object) throws IOException {
        return mapper.writeValueAsBytes(object);
    }

    public static TableEntity createMockedTable(TableLocationType locationType) {
        var table = new TableEntity(GUEST_NUMBER, locationType);
        table.setId(TABLE_ID);
        return table;
    }
}

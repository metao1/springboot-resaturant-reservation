package com.example.restaurant.mapper;


import com.example.restaurant.dto.ReservationResponse;
import com.example.restaurant.repository.model.ReservationEntity;
import com.example.restaurant.repository.model.TableEntity;
import com.example.restaurant.repository.model.TableLocationType;
import com.example.restaurant.service.util.ReservationMapperUtil;
import com.example.restaurant.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.restaurant.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class ReservationMapperTest {


    @Test
    public void testReservationToReservationResponseMapThenSuccessful() {

        // given
        var locationTypes = new TableLocationType[]{
                TableLocationType.INDOOR_ROOM,
                TableLocationType.INDOOR_WINDOW,
                TableLocationType.OUTDOOR_STREET,
                TableLocationType.OUTDOOR_YARD
        };

        // for each location type
        for (TableLocationType locationType : locationTypes) {
            // given
            TableEntity table = TestUtil.createMockedTable(locationType);

            // when
            ReservationEntity reservation = TestUtil.createMockedReservation(locationType, GUEST_NUMBER, table);
            ReservationResponse response = ReservationMapperUtil.mapToResponse(reservation);

            // then
            assertEquals(TRACKING_ID, response.trackingId());
            assertEquals(TABLE_ID.toString(), response.tableId());
            assertEquals(locationType.name(), response.tableLocationType());
            assertEquals(GUEST_NUMBER, response.guestNumber());
            assertEquals(RESERVATION_DATE, response.reservationDate());
            assertEquals(ARRIVAL_TIME, response.arrivalTime());
            assertEquals(DEPARTURE_TIME, response.departureTime());
        }
    }
}
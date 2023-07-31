package com.example.restaurant.service.util;

import com.example.restaurant.dto.ReservationResponse;
import com.example.restaurant.repository.model.ReservationEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReservationMapperUtil {

    public static ReservationResponse mapToResponse(ReservationEntity reservation) {
        return new ReservationResponse(
                reservation.getTrackingId(),
                reservation.getTable().getId().toString(),
                reservation.getTable().getLocationType().name(),
                reservation.getGuestNumber(),
                reservation.getReservationDate(),
                reservation.getArrivalTime(),
                reservation.getDepartureTime()
        );
    }
}

package com.example.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationResponse(
        String trackingId,
        String tableId,
        String tableLocationType,
        int guestNumber,
        @JsonFormat(pattern = "yy-MM-dd") LocalDate reservationDate,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime arrivalTime,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime departureTime
) {
}

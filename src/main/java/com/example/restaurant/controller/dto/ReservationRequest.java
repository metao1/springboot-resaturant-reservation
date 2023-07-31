package com.example.restaurant.controller.dto;

import com.example.restaurant.model.TableLocationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservationRequest(
        @NotNull TableLocationType tableOption,
        @Min(1) int seatNum,
        @NotNull @JsonFormat(pattern = "yy-MM-dd") @Future LocalDate reservationDate,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime arrivalTime,
        @NotNull @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime departureTime
) {
}


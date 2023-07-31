package com.example.restaurant.controller;

import com.example.restaurant.controller.dto.ReservationRequest;
import com.example.restaurant.controller.dto.ReservationResponse;
import com.example.restaurant.model.ReservationNotFoundException;
import com.example.restaurant.service.ReservationService;
import com.example.restaurant.service.util.ReservationMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @GetMapping("/{trackingId}")
    public ResponseEntity<ReservationResponse> getByTrackingId(@PathVariable String trackingId) {
        Optional<ReservationResponse> reservation = reservationService.getReservation(trackingId);
        return reservation.map(ResponseEntity::ok)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
    }

    @PostMapping
    public ReservationResponse reserve(@RequestBody ReservationRequest reservationRequest) {
        var reservation = reservationService.createReservation(
                reservationRequest.seatNum(),
                reservationRequest.reservationDate(),
                reservationRequest.arrivalTime(),
                reservationRequest.departureTime(),
                reservationRequest.tableOption()
        );
        return ReservationMapperUtil.mapToResponse(reservation);
    }

    @DeleteMapping("/{trackingId}")
    public ResponseEntity<Void> cancel(@PathVariable String trackingId) {
        reservationService.cancelReservation(trackingId);
        return ResponseEntity.noContent().build();
    }
}

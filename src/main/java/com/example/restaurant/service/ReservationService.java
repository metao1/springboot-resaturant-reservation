package com.example.restaurant.service;

import com.example.restaurant.dto.ReservationResponse;
import com.example.restaurant.repository.ReservationRepository;
import com.example.restaurant.repository.model.*;
import com.example.restaurant.service.factory.ReservationFactory;
import com.example.restaurant.service.strategy.TableReservationStrategy;
import com.example.restaurant.service.util.ReservationMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TableService tableService;
    private final TableReservationStrategy tableReservationStrategy;

    public ReservationEntity createReservation(int guestNum,
                                               LocalDate dayOfReservation,
                                               LocalDateTime arrivalTime,
                                               LocalDateTime departureTime,
                                               TableLocationType locationType) {
        List<TableEntity> tables = tableService.getTablesByLocationType(locationType);
        ReservationEntity reservation = ReservationFactory.createReservation(
                locationType, guestNum, dayOfReservation, arrivalTime, departureTime
        );

        Optional<TableEntity> possibleTable = findSuitableTable(tables, reservation);
        possibleTable.ifPresentOrElse(table -> {
            table.addReservation(reservation);
            reservation.setTable(table);
            reservation.setStatus(ReservationStatus.RESERVED);
        }, () -> {
            throw new TableNotFoundException("No suitable table found for the reservation");
        });

        reservationRepository.save(reservation);
        return reservation;
    }

    private Optional<TableEntity> findSuitableTable(List<TableEntity> tables, ReservationEntity reservation) {
        return tables.stream()
                .filter(table -> tableReservationStrategy.isReservationApplicableToTable(table, reservation))
                .findFirst();
    }

    public void cancelReservation(String trackingId) {
        var reservation = reservationRepository.findByTrackingIdAndStatus(trackingId, ReservationStatus.RESERVED);
        reservation.ifPresentOrElse(r -> r.setStatus(ReservationStatus.CANCELLED), () -> {
            throw new ReservationNotFoundException("Reservation not found");
        });
    }

    public Optional<ReservationResponse> getReservation(String trackingId) {
        var reservation = reservationRepository.findByTrackingIdAndStatus(trackingId, ReservationStatus.RESERVED);
        return reservation.map(ReservationMapperUtil::mapToResponse);
    }
}

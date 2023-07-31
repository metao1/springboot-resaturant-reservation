package com.example.restaurant.service.strategy;

import com.example.restaurant.repository.ReservationRepository;
import com.example.restaurant.model.ReservationEntity;
import com.example.restaurant.model.TableEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TableReservationStrategy implements ReservationStrategy {

    private static final int DAY_AFTER_TOMORROW = 2;
    private final ReservationRepository reservationRepository;

    @Override
    public boolean isReservationApplicableToTable(TableEntity table, ReservationEntity reservation) {
        // Check if the provided table matches the desired location type and has sufficient capacity
        if (table == null) return false;
        if (table.getLocationType() != reservation.getLocationType()) return false;
        if (table.getCapacity() < reservation.getGuestNumber()) return false;

        var today = LocalDate.now();
        if (reservation.getReservationDate().isBefore(today) ||
                reservation.getReservationDate().isAfter(today.plusDays(DAY_AFTER_TOMORROW))) {
            return false;
        }

        // Check if the table has any overlapping reservations with the new reservation
        var existingReservations = getReservationsForTable(table);
        return existingReservations.stream().noneMatch(reservation::overlapsWith);
    }

    public List<ReservationEntity> getReservationsForTable(TableEntity table) {
        return reservationRepository.findByTable(table);
    }
}

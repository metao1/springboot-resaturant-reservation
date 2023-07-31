package com.example.restaurant.service.strategy;

import com.example.restaurant.model.ReservationEntity;
import com.example.restaurant.model.TableEntity;

public interface ReservationStrategy {

    /**
     * Checks if the given reservation can be made in current tables.
     *
     * @param table       table to be checked
     * @param reservation reservation to be made
     * @return true if the reservation can be made, false otherwise
     */
    boolean isReservationApplicableToTable(TableEntity table, ReservationEntity reservation);
}

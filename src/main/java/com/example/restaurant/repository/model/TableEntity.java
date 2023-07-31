package com.example.restaurant.repository.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "table_entity")
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int capacity;
    private TableLocationType locationType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationEntity> reservations = new ArrayList<>();

    public TableEntity() {
    }

    public TableEntity(int capacity, TableLocationType locationType) {
        this.capacity = capacity;
        this.locationType = locationType;
    }

    public void addReservation(ReservationEntity reservation) {
        reservations.add(reservation);
        reservation.setTable(this);
    }

}

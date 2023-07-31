package com.example.restaurant.repository.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "reservation_entity")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    String trackingId;

    ReservationStatus status;

    TableLocationType locationType;
    int guestNumber;
    LocalDate reservationDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime arrivalTime;

    LocalDateTime departureTime;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "table_id")
    TableEntity table;

    public ReservationEntity() {
    }

    public boolean overlapsWith(ReservationEntity other) {
        if (other == null) {
            return false;
        }
        return this.reservationDate.equals(other.reservationDate) &&
                this.arrivalTime.isBefore((other.departureTime)) &&
                this.departureTime.isAfter(other.arrivalTime);
    }

}

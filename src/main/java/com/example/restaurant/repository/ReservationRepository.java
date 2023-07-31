package com.example.restaurant.repository;

import com.example.restaurant.repository.model.ReservationEntity;
import com.example.restaurant.repository.model.ReservationStatus;
import com.example.restaurant.repository.model.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    List<ReservationEntity> findByTable(TableEntity table);

    @Query("SELECT r FROM ReservationEntity r WHERE r.trackingId = :trackingId AND r.status = :status")
    Optional<ReservationEntity> findByTrackingIdAndStatus(String trackingId, ReservationStatus status);
}

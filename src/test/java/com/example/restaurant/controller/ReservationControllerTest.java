package com.example.restaurant.controller;

import com.example.restaurant.dto.ReservationRequest;
import com.example.restaurant.repository.ReservationRepository;
import com.example.restaurant.repository.model.ReservationEntity;
import com.example.restaurant.repository.model.ReservationStatus;
import com.example.restaurant.repository.model.TableLocationType;
import com.example.restaurant.service.ReservationService;
import com.example.restaurant.service.util.ReservationMapperUtil;
import com.example.restaurant.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static com.example.restaurant.util.TestConstants.GUEST_NUMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Import({ReservationService.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReservationControllerTest {

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yy-MM-dd");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ReservationRepository reservationRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<ReservationEntity> reservationCaptor;

    @Test
    public void testReserveTableSuccessful() throws Exception {
        // given
        var locationType = TableLocationType.INDOOR_ROOM;
        var today = LocalDate.now();
        var arrivalTime = LocalDateTime.now();
        var reservationRequest = new ReservationRequest(
                locationType,
                GUEST_NUMBER,
                today,
                arrivalTime,
                arrivalTime.plus(2, ChronoUnit.HOURS)
        );
        var table = TestUtil.createMockedTable(locationType);
        var reservation = TestUtil.createMockedReservation(locationType, GUEST_NUMBER, table);
        var response = ReservationMapperUtil.mapToResponse(reservation);

        // when & then
        mockMvc.perform(post("/api/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(objectMapper, reservationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackingId").exists())
                .andExpect(jsonPath("$.tableId").exists())
                .andExpect(jsonPath("$.guestNumber").value(response.guestNumber()))
                .andExpect(jsonPath("$.arrivalTime").value(response.arrivalTime().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.departureTime").value(response.departureTime().format(dateTimeFormatter)))
                .andExpect(jsonPath("$.reservationDate").value(response.reservationDate().format(dateFormatter)))
                .andExpect(jsonPath("$.tableLocationType").value(response.tableLocationType()));

        verify(reservationRepository).save(reservationCaptor.capture());
    }

    @Test
    public void testReserveTableTableNotFound() throws Exception {
        // given
        var locationType = TableLocationType.INDOOR_ROOM;
        var today = LocalDate.now();
        var arrivalTime = LocalDateTime.now();
        var reservationRequest = new ReservationRequest(
                locationType,
                10,
                today,
                arrivalTime,
                arrivalTime.plus(2, ChronoUnit.HOURS)
        );

        // when & then
        mockMvc.perform(post("/api/reservation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(objectMapper, reservationRequest)))
                .andExpect(status().isNotFound());

        verify(reservationRepository, never()).save(reservationCaptor.capture());
    }


    @Test
    public void testCancelReservationSuccessful() throws Exception {

        // given
        var locationType = TableLocationType.INDOOR_ROOM;
        var table = TestUtil.createMockedTable(locationType);
        var reservation = TestUtil.createMockedReservation(locationType, GUEST_NUMBER, table);
        var trackingId = reservation.getTrackingId();

        when(reservationRepository.findByTrackingIdAndStatus(trackingId, ReservationStatus.RESERVED))
                .thenReturn(Optional.of(reservation));

        // when
        mockMvc.perform(delete("/api/reservation/{trackingId}", trackingId))
                .andExpect(status().isNoContent());

        // then
        verify(reservationRepository).findByTrackingIdAndStatus(trackingId, ReservationStatus.RESERVED);
        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
    }
}

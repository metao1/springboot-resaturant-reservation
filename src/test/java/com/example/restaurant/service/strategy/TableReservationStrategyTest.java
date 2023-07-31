package com.example.restaurant.service.strategy;

import com.example.restaurant.util.TestUtil;
import com.example.restaurant.repository.ReservationRepository;
import com.example.restaurant.repository.model.ReservationEntity;
import com.example.restaurant.repository.model.TableEntity;
import com.example.restaurant.repository.model.TableLocationType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static com.example.restaurant.util.TestConstants.GUEST_NUMBER;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TableReservationStrategyTest {

    private TableEntity table;

    @BeforeAll
    public void setup() {
        table = TestUtil.createMockedTable(TableLocationType.INDOOR_ROOM);
    }

    @Test
    public void testIsReservationApplicableToTableWhenNoOverlapping() {
        // given
        var locationType = TableLocationType.INDOOR_ROOM;
        ReservationEntity reservation = TestUtil.createMockedReservation(locationType, GUEST_NUMBER, table);
        ReservationRepository reservationRepository = mock(ReservationRepository.class);
        when(reservationRepository.findByTable(table)).thenReturn(List.of());

        // when
        TableReservationStrategy strategy = new TableReservationStrategy(reservationRepository);
        boolean reservationIsApplicable = strategy.isReservationApplicableToTable(table, reservation);

        // Then
        assertTrue(reservationIsApplicable);
    }

}

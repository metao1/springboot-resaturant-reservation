package com.example.restaurant.service;

import com.example.restaurant.repository.TableRepository;
import com.example.restaurant.repository.model.TableEntity;
import com.example.restaurant.repository.model.TableLocationType;
import com.example.restaurant.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Collections;
import java.util.List;

import static com.example.restaurant.util.TestConstants.GUEST_NUMBER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TableServiceTest {

    private final TableRepository tableRepository = mock(TableRepository.class);

    @Test
    public void testCreateTable() {
        var locationType = TableLocationType.INDOOR_ROOM;

        // given
        TableEntity table = TestUtil.createMockedTable(locationType);
        table.setId(null);
        when(tableRepository.save(table)).thenReturn(table);

        // when
        TableService service = new TableService(tableRepository);
        TableEntity result = service.createTable(GUEST_NUMBER, locationType);

        // Verify the results
        assertEquals(table, result);
        verify(tableRepository).save(table);
    }

    @Test
    public void testGetTables() {
        // given
        var locationType = TableLocationType.INDOOR_WINDOW;
        TableEntity table = TestUtil.createMockedTable(locationType);
        List<TableEntity> tables = List.of(table);
        when(tableRepository.findByTableLocationType(locationType)).thenReturn(tables);

        // when
        TableService service = new TableService(tableRepository);
        List<TableEntity> result = service.getTablesByLocationType(locationType);

        // then
        assertEquals(tables, result);
    }

    @Test
    public void testGetTablesWithNoMatchingTables() {
        // given
        var locationType = TableLocationType.INDOOR_WINDOW;
        when(tableRepository.findByTableLocationType(locationType)).thenReturn(Collections.emptyList());

        TableService service = new TableService(tableRepository);

        // when
        List<TableEntity> result = service.getTablesByLocationType(locationType);

        // then
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetTablesWithNullTableOption() {
        // given
        TableRepository tableRepository = mock(TableRepository.class);

        TableService service = new TableService(tableRepository);

        // when
        List<TableEntity> result = service.getTablesByLocationType(null);

        // then
        assertTrue(result.isEmpty());
    }
}
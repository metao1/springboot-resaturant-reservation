package com.example.restaurant.service;

import com.example.restaurant.repository.TableRepository;
import com.example.restaurant.model.TableEntity;
import com.example.restaurant.model.TableLocationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;

    /**
     * Creates a table with the given capacity and location type.
     *
     * @param capacity     the capacity of the table (number of seats)
     * @param locationType the location type of the table (indoor or outdoor)
     * @return the created table
     */
    public TableEntity createTable(int capacity, TableLocationType locationType) {
        TableEntity table = new TableEntity(capacity, locationType);
        tableRepository.save(table);
        return table;
    }

    public List<TableEntity> getTablesByLocationType(TableLocationType locationType) {
        return tableRepository.findByTableLocationType(locationType);
    }
}

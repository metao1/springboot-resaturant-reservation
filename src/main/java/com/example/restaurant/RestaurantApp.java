package com.example.restaurant;

import com.example.restaurant.repository.TableRepository;
import com.example.restaurant.model.TableEntity;
import com.example.restaurant.model.TableLocationType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@SpringBootApplication
@RequiredArgsConstructor
public class RestaurantApp {
    private final TableRepository tableRepository;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantApp.class, args);
    }

    @PostConstruct
    @Transactional
    public void initializer() {
        var tables = Stream.of("OUTDOOR_YARD", "OUTDOOR_STREET", "INDOOR_WINDOW", "INDOOR_ROOM")
                .map(type -> new TableEntity(6, TableLocationType.valueOf(type)))
                .toList();
        tableRepository.saveAll(tables);
    }
}

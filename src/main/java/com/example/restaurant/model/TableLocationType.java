package com.example.restaurant.model;

import lombok.Getter;

@Getter
public enum TableLocationType {

    OUTDOOR_YARD("OUTDOOR_YARD"),
    OUTDOOR_STREET("OUTDOOR_STREET"),
    INDOOR_WINDOW("OUTDOOR_STREET"),
    INDOOR_ROOM("INDOOR_ROOM");

    private final String name;

    TableLocationType(String name) {
        this.name = name;
    }
}

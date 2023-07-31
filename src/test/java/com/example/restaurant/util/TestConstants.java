package com.example.restaurant.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class TestConstants {

    public static final String TRACKING_ID = "123";
    public static final Long TABLE_ID = 1L;
    public static final int GUEST_NUMBER = 4;
    public static final LocalDate RESERVATION_DATE = LocalDate.now();
    public static final LocalDateTime ARRIVAL_TIME = LocalDateTime.now();
    public static final LocalDateTime DEPARTURE_TIME = ARRIVAL_TIME.plusHours(2);

}

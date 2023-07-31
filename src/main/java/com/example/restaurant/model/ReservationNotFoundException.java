package com.example.restaurant.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ReservationNotFoundException extends RuntimeException {
    public ReservationNotFoundException(String msg) {
        super(msg);
    }
}

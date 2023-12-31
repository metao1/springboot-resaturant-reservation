package com.example.restaurant.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TableNotFoundException extends RuntimeException {
    public TableNotFoundException(String msg) {
        super(msg);
    }
}

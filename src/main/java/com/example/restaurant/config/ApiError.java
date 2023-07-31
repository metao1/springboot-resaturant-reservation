package com.example.restaurant.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
class ApiError {

   HttpStatus status;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy-MM-dd'T'hh:mm:ss")
   LocalDateTime timestamp;
   String message;

   private ApiError() {
      timestamp = LocalDateTime.now();
   }

   ApiError(HttpStatus status, String message) {
      this();
      this.status = status;
      this.message = message;
   }
}

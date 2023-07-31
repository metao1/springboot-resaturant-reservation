# Restaurant Reservation API

The backend of the reservation website for "Joe's Diner" involves the following process:
Restaurant Reservation API
This project is a Spring Boot application that provides a REST API for managing restaurant reservations.

## Requirements

Java 17
Maven

## Getting Started
Clone the repository and after getting into the project directory, run the following command:

```mvn spring-boot:run```

## API Endpoints

The following REST API endpoints are provided:

`GET /reservation/{id}`: Returns a reservation object for the given ID. If no reservation is found, returns a custom
status.

`POST /reservation`: Creates a new reservation using the provided reservation object. If successful, returns the newly
created reservation object. Otherwise, returns a custom status indicating any validation errors or if no tables are
available for the given time interval.

`DELETE /reservation/{id}`: Cancels an existing reservation with the given ID. If the reservation is already cancelled
or does not exist, returns a custom status code.

`GET /restaurant`: Returns information about the restaurant, including its name, number of tables, and reservations.

## Initial Data

On startup, the following initial data is created:

4 tables with 6 chairs each, located in the following areas: indoor window, indoor inside, outdoor street, and outdoor
yard.

## Error Handling

An exception handler controller is implemented

Responses are wrapped in a custom generic object that includes a response body, a custom response status object (enum).

## Database

An in-memory database such as H2 is used to store initial data and reservations created through the API.

## What remained to be done

1. Restaurant Entity and its service, controller to handler Restaurant entity
2. Tests related to Restaurant service and its controller
3. An errors list which would be empty if no errors occurred

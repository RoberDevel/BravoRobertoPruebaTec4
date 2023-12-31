package com.roberdev.gestionturismo.dto;

import com.roberdev.gestionturismo.model.enums.FlightSeatType;

import java.time.LocalDate;

public class CreateFlightDTO {

    private String origin;
    private String destination;
    private FlightSeatType seatType;
    private LocalDate date;
}

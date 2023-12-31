package com.roberdev.gestionturismo.dto;

import com.roberdev.gestionturismo.model.enums.FlightSeatType;

import java.util.List;

public class CreateFlightReservationDTO {

    private String flightToCode;
    private String flightBackCode;
    private String dateFlightTo;
    private String dateFlightBack;
    private FlightSeatType seatTypeFlightTo;
    private FlightSeatType seatTypeFlightBack;
    private List<PersonDTO> passengers;
    
}

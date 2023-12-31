package com.roberdev.gestionturismo.dto;

import com.roberdev.gestionturismo.model.enums.FlightSeatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFlightDTO {

    private String origin;
    private String destination;
    private FlightSeatType seatType;
    private LocalDate date;
    private Integer totalSeats;
}
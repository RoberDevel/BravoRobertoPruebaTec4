package com.roberdev.gestionturismo.dto;

import com.roberdev.gestionturismo.model.enums.FlightSeatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFlightReservationDTO {

    private String flightToCode;
    private String flightBackCode;
    private LocalDate dateFlightTo;
    private LocalDate dateFlightBack;
    private FlightSeatType seatTypeFlightTo;
    private FlightSeatType seatTypeFlightBack;
    private List<PersonDTO> passengers;

}

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
public class FlightDTO {

    private String flightNumber;
    private String origin;
    private String destination;
    private FlightSeatType seatType;
    private Double seatPrice;
    private Boolean isFull;
    private LocalDate date;
    private Integer totalSeats;
    private List<FlightReservationDTO> flightReservations;

}

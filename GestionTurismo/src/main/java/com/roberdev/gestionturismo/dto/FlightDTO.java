package com.roberdev.gestionturismo.dto;

import com.roberdev.gestionturismo.model.enums.FlightSeatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDTO {

    private String flightNumber;
    private String origin;
    private String destination;
    private Map<FlightSeatType, Double> seatTypePrices;
    private Boolean isFull;
    private LocalDate date;
    private Integer totalSeats;
    private Boolean isActive;
    private List<LocalDate> statusChangeDates = new ArrayList<>();
    private List<FlightReservationDTO> flightReservations;

}

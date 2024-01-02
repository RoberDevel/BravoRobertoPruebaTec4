package com.roberdev.gestionturismo.dto;

import com.roberdev.gestionturismo.model.enums.FlightSeatType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditFlightDTO {

    private String origin;
    private String destination;
    private Map<FlightSeatType, Double> seatTypePrices;
    private LocalDate date;
    private Integer totalSeats;
    private Boolean isActive;

}

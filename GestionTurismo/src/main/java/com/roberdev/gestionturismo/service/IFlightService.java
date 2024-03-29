package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.dto.CreateFlightDTO;
import com.roberdev.gestionturismo.dto.EditFlightDTO;
import com.roberdev.gestionturismo.dto.FlightDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IFlightService {

    FlightDTO createFlight(CreateFlightDTO flight);

    List<FlightDTO> getAllFlights();

    List<FlightDTO> getFlightsByDateAndOriginAndDestination(LocalDate date, LocalDate date2, String origin, String destination);

    FlightDTO editFlight(String flightCode, Map<String, Object> updates);

    FlightDTO editFlightById(Long id, EditFlightDTO editFlightDTO);

    FlightDTO getFlightById(Long id);

    FlightDTO changeActiveStatus(String flightNumber, boolean isActive);


    // Double createFlightReservation(CreateFlightReservationDTO createFlightReservationDTO);

}

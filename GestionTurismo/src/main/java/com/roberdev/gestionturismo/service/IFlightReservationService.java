package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.dto.CreateFlightReservationDTO;
import com.roberdev.gestionturismo.dto.FlightReservationDTO;

import java.util.List;

public interface IFlightReservationService {

    String createFlightReservation(CreateFlightReservationDTO createFlightReservationDTO);

    List<FlightReservationDTO> getReservations();

    String cancelReservation(Long id);
}

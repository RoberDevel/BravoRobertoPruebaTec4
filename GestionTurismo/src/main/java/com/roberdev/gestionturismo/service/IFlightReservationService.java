package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.dto.CreateFlightReservationDTO;
import com.roberdev.gestionturismo.model.FlightReservation;

import java.util.List;

public interface IFlightReservationService {

    String createFlightReservation(CreateFlightReservationDTO createFlightReservationDTO);

    List<FlightReservation> getReservations();

    String cancelReservation(Long id);
}

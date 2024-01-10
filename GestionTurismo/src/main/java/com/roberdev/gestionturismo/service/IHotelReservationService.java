package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.dto.CreateHotelReservationDTO;
import com.roberdev.gestionturismo.dto.HotelReservationDTO;
import com.roberdev.gestionturismo.model.HotelReservation;

import java.util.List;

public interface IHotelReservationService {

    String createHotelReservation(CreateHotelReservationDTO createHotelReservationDTO);

    String cancelReservation(Long id);

    List<HotelReservationDTO> getReservations();
}

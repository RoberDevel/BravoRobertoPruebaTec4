package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.dto.CreateReservationDTO;
import com.roberdev.gestionturismo.dto.HotelReservationDTO;
import com.roberdev.gestionturismo.model.HotelReservation;

public interface IHotelReservationService {

    Double createHotelReservation(CreateReservationDTO createReservationDTO);

}

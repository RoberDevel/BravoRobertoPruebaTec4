package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.dto.CreateHotelDTO;
import com.roberdev.gestionturismo.dto.HotelDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface IHotelService {

    HotelDTO createHotel(CreateHotelDTO hotel);

    List<HotelDTO> getAllHotels();

    List<HotelDTO> getHotelsByDateAndCity(LocalDate date, LocalDate date2, String city);

    HotelDTO editHotel(String hotelCode, Map<String, Object> updates);

    HotelDTO editHotelById(Long id, String name, String city);

    HotelDTO changeActiveStatus(String hotelCode, boolean isActive);

    HotelDTO getHotelById(Long id);
}

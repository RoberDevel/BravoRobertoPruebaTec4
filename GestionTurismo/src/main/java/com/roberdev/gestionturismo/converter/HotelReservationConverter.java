package com.roberdev.gestionturismo.converter;

import com.roberdev.gestionturismo.dto.CreateHotelReservationDTO;
import com.roberdev.gestionturismo.dto.HotelReservationDTO;
import com.roberdev.gestionturismo.dto.PersonDTO;
import com.roberdev.gestionturismo.model.Hotel;
import com.roberdev.gestionturismo.model.HotelReservation;
import com.roberdev.gestionturismo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HotelReservationConverter implements Converter<HotelReservation, HotelReservationDTO> {

    @Autowired
    private PersonConverter personConverter;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public HotelReservationDTO convertToDTO(HotelReservation hotelReservation) {

        if (hotelReservation == null) {
            return null;
        }

        List<PersonDTO> personDTOList = new ArrayList<>();
        if (hotelReservation.getGuests() != null) {
            personDTOList = hotelReservation.getGuests().stream().map(personConverter::convertToDTO).toList();
        }

        HotelReservationDTO hotelReservationDTO = new HotelReservationDTO(hotelReservation.getHotelCode(), hotelReservation.getGuestsNumber(), hotelReservation.getNights(),
                hotelReservation.getCheckInDate(), hotelReservation.getCheckOutDate(), hotelReservation.getRoomType(), hotelReservation.getTotalPrice(), personDTOList);
        return hotelReservationDTO;
    }


    @Override
    public HotelReservation convertToEntity(HotelReservationDTO hotelReservationDTO) {

        if (hotelReservationDTO == null) {
            return null;
        }
        HotelReservation hotelReservation = new HotelReservation();
        hotelReservation.setGuestsNumber(hotelReservationDTO.getGuestsNumber());
        hotelReservation.setNights(hotelReservationDTO.getNights());
        hotelReservation.setCheckInDate(hotelReservationDTO.getCheckInDate());
        hotelReservation.setCheckOutDate(hotelReservationDTO.getCheckOutDate());
        hotelReservation.setRoomType(hotelReservationDTO.getRoomType());
        hotelReservation.setTotalPrice(hotelReservationDTO.getTotalPrice());
        if (hotelReservationDTO.getGuests() != null) {
            hotelReservation.setGuests(hotelReservationDTO.getGuests().stream().map(personConverter::convertToEntity).toList());
        }

        return hotelReservation;
    }

    public HotelReservation convertCreateReservationDTOToHotelReservation(CreateHotelReservationDTO createHotelReservationDTO) {

        if (createHotelReservationDTO == null) {
            return null;
        }
        HotelReservation hotelReservation = new HotelReservation();
        hotelReservation.setHotelCode(createHotelReservationDTO.getHotelCode());
        hotelReservation.setCheckInDate(createHotelReservationDTO.getCheckInDate());
        hotelReservation.setCheckOutDate(createHotelReservationDTO.getCheckOutDate());
        hotelReservation.setRoomType(createHotelReservationDTO.getRoomType());
        hotelReservation.setGuests(createHotelReservationDTO.getGuests().stream().map(personConverter::convertToEntity).toList());

        return hotelReservation;
    }


    public List<HotelReservationDTO> convertToDTOList(List<HotelReservation> reservations) {

        if (reservations == null) {
            return null;
        }

        List<HotelReservationDTO> hotelReservationDTOS = new ArrayList<>();
        for (HotelReservation hotelReservation : reservations) {
            hotelReservationDTOS.add(convertToDTO(hotelReservation));
        }
        return hotelReservationDTOS;
    }
}

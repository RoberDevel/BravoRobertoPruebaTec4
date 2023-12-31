package com.roberdev.gestionturismo.converter;

import com.roberdev.gestionturismo.dto.CreateReservationDTO;
import com.roberdev.gestionturismo.dto.HotelReservationDTO;
import com.roberdev.gestionturismo.dto.PersonDTO;
import com.roberdev.gestionturismo.model.Hotel;
import com.roberdev.gestionturismo.model.HotelReservation;
import com.roberdev.gestionturismo.model.Person;
import com.roberdev.gestionturismo.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        Hotel hotel = hotelRepository.findByHotelCode(hotelReservationDTO.getHotelCode());


        return hotelReservation;
    }

    public HotelReservation convertCreateReservationDTOToHotelReservation(CreateReservationDTO createReservationDTO) {

        if (createReservationDTO == null) {
            return null;
        }
        HotelReservation hotelReservation = new HotelReservation();
        hotelReservation.setHotelCode(createReservationDTO.getHotelCode());
        hotelReservation.setCheckInDate(createReservationDTO.getCheckInDate());
        hotelReservation.setCheckOutDate(createReservationDTO.getCheckOutDate());
        hotelReservation.setRoomType(createReservationDTO.getRoomType());
        hotelReservation.setGuests(createReservationDTO.getGuests().stream().map(personConverter::convertToEntity).toList());
        
        return hotelReservation;
    }


}

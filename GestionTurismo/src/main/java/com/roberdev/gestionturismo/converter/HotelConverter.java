package com.roberdev.gestionturismo.converter;

import com.roberdev.gestionturismo.dto.CreateHotelDTO;
import com.roberdev.gestionturismo.dto.HotelDTO;
import com.roberdev.gestionturismo.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class HotelConverter implements Converter<Hotel, HotelDTO> {

    @Autowired
    private HotelReservationConverter hotelReservationConverter;


    @Autowired
    private RoomConverter roomConverter;

    public HotelConverter(RoomConverter roomConverter) {
        this.roomConverter = roomConverter;
    }

    @Override
    public HotelDTO convertToDTO(Hotel hotel) {

        if (hotel == null) {
            return null;
        }
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setHotelCode(hotel.getHotelCode());
        hotelDTO.setName(hotel.getName());
        hotelDTO.setCity(hotel.getCity());
        hotelDTO.setIsActive(hotel.getIsActive());
        hotelDTO.setStatusChangeDates(hotel.getStatusChangeDates());

        if (hotel.getRooms() != null) {
            hotelDTO.setRooms(hotel.getRooms().stream()
                    .map(roomConverter::convertToDTO).toList());
        }


        return hotelDTO;
    }

    @Override
    public Hotel convertToEntity(HotelDTO hotelDTO) {

        if (hotelDTO == null) {
            return null;
        }

        Hotel hotel = new Hotel();
        hotel.setHotelCode(hotelDTO.getHotelCode());
        hotel.setName(hotelDTO.getName());
        hotel.setCity(hotelDTO.getCity());
        hotel.setIsActive(hotelDTO.getIsActive());
        hotel.setStatusChangeDates(hotelDTO.getStatusChangeDates());

        if (hotelDTO.getRooms() != null) {
            hotel.setRooms(hotelDTO.getRooms().stream()
                    .map(roomConverter::convertToEntity).toList());
        }

        return hotel;
    }

    public HotelDTO convertCreateHotelDTOToHotelDTO(CreateHotelDTO createHotelDTO) {
        HotelDTO hotelDTO = new HotelDTO();
        hotelDTO.setName(createHotelDTO.getName());
        hotelDTO.setCity(createHotelDTO.getCity());
        return hotelDTO;
    }


}

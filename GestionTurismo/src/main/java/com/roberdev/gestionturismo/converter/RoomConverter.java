package com.roberdev.gestionturismo.converter;

import com.roberdev.gestionturismo.dto.RoomDTO;
import com.roberdev.gestionturismo.model.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomConverter implements Converter<Room, RoomDTO> {

    @Autowired
    private HotelReservationConverter hotelReservationConverter;


    @Override
    public RoomDTO convertToDTO(Room room) {
        if (room == null) {
            return null;
        }

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setAvailableFrom(room.getAvailableFrom());
        roomDTO.setAvailableTo(room.getAvailableTo());
        roomDTO.setPricePerNight(room.getPricePerNight());
        if (room.getHotelReservations() != null) {
            roomDTO.setReservations(room.getHotelReservations().stream()
                    .map(hotelReservationConverter::convertToDTO
                    ).toList());
        }

        return roomDTO;
    }

    @Override
    public Room convertToEntity(RoomDTO roomDTO) {
        if (roomDTO == null) {
            return null;
        }

        Room room = new Room();
        room.setRoomType(roomDTO.getRoomType());
        room.setAvailableFrom(roomDTO.getAvailableFrom());
        room.setAvailableTo(roomDTO.getAvailableTo());
        room.setPricePerNight(roomDTO.getPricePerNight());
        room.setHotelReservations(roomDTO.getReservations().stream()
                .map(hotelReservationConverter::convertToEntity).toList());

        return room;
    }

    public List<RoomDTO> convertToDTOList(List<Room> rooms) {
        if (rooms == null) {
            return Collections.emptyList();
        }

        return rooms.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


}

package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.converter.RoomConverter;
import com.roberdev.gestionturismo.dto.*;
import com.roberdev.gestionturismo.model.Hotel;
import com.roberdev.gestionturismo.model.HotelReservation;
import com.roberdev.gestionturismo.model.Person;
import com.roberdev.gestionturismo.model.Room;
import com.roberdev.gestionturismo.repository.HotelRepository;
import com.roberdev.gestionturismo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService implements IRoomService {
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomConverter roomConverter;

    @Override
    public RoomDTO createRoom(String HotelCode, CreateRoomDTO createRoomDTO) {

        if (createRoomDTO.getAvailableFrom().isAfter(createRoomDTO.getAvailableTo())) {
            return null;
        }

        Hotel hotel = hotelRepository.findByHotelCode(HotelCode);
        Room room = new Room();
        if (hotel == null) {
            return null;
        }
        room.setHotel(hotel);
        room.setRoomType(createRoomDTO.getRoomType());
        room.setPricePerNight(createRoomDTO.getPricePerNight());
        room.setAvailableFrom(createRoomDTO.getAvailableFrom());
        room.setAvailableTo(createRoomDTO.getAvailableTo());

        roomRepository.save(room);

        return roomConverter.convertToDTO(room);
    }

    @Override
    public List<RoomDTO> getAllRooms() {

        List<Room> rooms = roomRepository.findAll();
        List<RoomDTO> roomDTOS = rooms.stream().map(roomConverter::convertToDTO).collect(Collectors.toList());


        return roomDTOS;
    }


}

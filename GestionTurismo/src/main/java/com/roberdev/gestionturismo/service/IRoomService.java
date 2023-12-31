package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.dto.CreateRoomDTO;
import com.roberdev.gestionturismo.dto.RoomDTO;

import java.util.List;

public interface IRoomService {

    RoomDTO createRoom(String HotelCode, CreateRoomDTO createRoomDTO);

    List<RoomDTO> getAllRooms();
}

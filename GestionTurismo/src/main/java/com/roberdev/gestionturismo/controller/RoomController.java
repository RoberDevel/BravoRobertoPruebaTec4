package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateRoomDTO;
import com.roberdev.gestionturismo.dto.RoomDTO;
import com.roberdev.gestionturismo.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class RoomController {

    @Autowired
    IRoomService roomService;

    @PostMapping("/rooms/new/{hotelCode}")
    public ResponseEntity<RoomDTO> createRoom(@PathVariable String hotelCode, @RequestBody CreateRoomDTO createRoomDTO) {


        RoomDTO roomDTO = roomService.createRoom(hotelCode, createRoomDTO);

        if (roomDTO == null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(roomDTO);
    }

    @GetMapping("/rooms")
    public ResponseEntity<List<RoomDTO>> obtenerHabitaciones() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

}

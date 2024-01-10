package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateRoomDTO;
import com.roberdev.gestionturismo.dto.RoomDTO;
import com.roberdev.gestionturismo.service.IRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Room created"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Error creating room")
    })
    @Operation(summary = "Create a new room")
    public ResponseEntity<?> createRoom(@PathVariable String hotelCode, @RequestBody CreateRoomDTO createRoomDTO) {


        RoomDTO roomDTO = roomService.createRoom(hotelCode, createRoomDTO);

        if (roomDTO == null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating room");
        }

        return ResponseEntity.ok(roomDTO);
    }

    @GetMapping("/rooms")
    @Operation(summary = "Get rooms")
    public ResponseEntity<List<RoomDTO>> obtenerHabitaciones() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

}

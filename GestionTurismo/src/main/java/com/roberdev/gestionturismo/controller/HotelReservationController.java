package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateHotelReservationDTO;
import com.roberdev.gestionturismo.dto.HotelReservationDTO;
import com.roberdev.gestionturismo.service.IHotelReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class HotelReservationController {

    @Autowired
    IHotelReservationService hotelReservationService;

    @PostMapping("/hotel-booking/new")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Hotel reservation created"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Error creating hotel reservation")
    })
    @Operation(summary = "Create a new hotel reservation")
    public ResponseEntity<?> createHotelReservation(@RequestBody CreateHotelReservationDTO createHotelReservationDTO) {

        String result = hotelReservationService.createHotelReservation(createHotelReservationDTO);

        if (result.contains("Error")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);

    }


    @DeleteMapping("/hotel-booking/cancel/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel reservation cancelled"),
            @ApiResponse(responseCode = "400", description = "Hotel reservation not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Cancel hotel reservation")
    public ResponseEntity<String> cancelReservation(@PathVariable Long id) {

        String result = hotelReservationService.cancelReservation(id);
        if (result.contains("Error"))
            return ResponseEntity.badRequest().body(result);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/hotel-booking/all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations found"),
            @ApiResponse(responseCode = "204", description = "No reservations found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Get hotel reservations")
    public ResponseEntity<?> getReservations() {
        List<HotelReservationDTO> reservations = hotelReservationService.getReservations();
        if (reservations.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok().body(reservations);
    }


}

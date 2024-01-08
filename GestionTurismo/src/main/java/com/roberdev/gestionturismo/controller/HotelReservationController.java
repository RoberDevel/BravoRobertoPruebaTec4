package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateHotelReservationDTO;
import com.roberdev.gestionturismo.model.HotelReservation;
import com.roberdev.gestionturismo.service.IHotelReservationService;
import io.swagger.v3.oas.annotations.Operation;
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

        Double totalPrice = hotelReservationService.createHotelReservation(createHotelReservationDTO);

        if (totalPrice == null) {
            return ResponseEntity.badRequest().body("Error creating hotel reservation");
        }
        return ResponseEntity.ok(totalPrice);

    }


    @DeleteMapping("/hotel-booking/cancel/{id}")
    @Operation(summary = "Cancel hotel reservation")
    public ResponseEntity<String> cancelReservation(@PathVariable Long id) {

        String result = hotelReservationService.cancelReservation(id);
        if (result.isBlank())
            return ResponseEntity.badRequest().body("Error cancelling reservation");

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/hotel-booking/all")
    @Operation(summary = "Get hotel reservations")
    public ResponseEntity<?> getReservations() {
        List<HotelReservation> reservations = hotelReservationService.getReservations();
        if (reservations == null)
            return ResponseEntity.badRequest().body("Error getting reservations");

        return ResponseEntity.ok().body(reservations);
    }


}

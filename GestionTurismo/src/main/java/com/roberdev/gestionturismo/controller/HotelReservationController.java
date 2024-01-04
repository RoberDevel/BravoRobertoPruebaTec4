package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateHotelReservationDTO;
import com.roberdev.gestionturismo.service.IHotelReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}

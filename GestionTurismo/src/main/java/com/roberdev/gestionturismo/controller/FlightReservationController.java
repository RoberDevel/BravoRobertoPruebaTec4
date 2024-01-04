package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateFlightReservationDTO;
import com.roberdev.gestionturismo.dto.CreateHotelReservationDTO;
import com.roberdev.gestionturismo.model.FlightReservation;
import com.roberdev.gestionturismo.service.IFlightReservationService;
import com.roberdev.gestionturismo.service.IFlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agency")
public class FlightReservationController {

    @Autowired
    IFlightReservationService flightReservationService;

    @PostMapping("/flight-booking/new")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Flight reservation created"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Error creating flight reservation")
    })
    @Operation(summary = "Create a new flight reservation")
    public ResponseEntity<?> createFlightReservation(@RequestBody CreateFlightReservationDTO createFlightReservationDTO) {

        Double price = flightReservationService.createFlightReservation(createFlightReservationDTO);


        if (price == null) {
            return ResponseEntity.badRequest().body("Error creating flight reservation");
        }

        return ResponseEntity.ok().body(price);
    }

    @GetMapping("/flight-booking/all")
    @Operation(summary = "Get flight reservations")
    public ResponseEntity<?> getReservations() {

        List<FlightReservation> reservations = flightReservationService.getReservations();
        if (reservations == null)
            return ResponseEntity.badRequest().body("Error getting reservations");

        return ResponseEntity.ok().body(reservations);
    }

    @DeleteMapping("/flight-booking/cancel/{id}")
    @Operation(summary = "Cancel flight reservation")
    public ResponseEntity<String> cancelReservation(@PathVariable Long id) {

        String result = flightReservationService.cancelReservation(id);
        if (result.isBlank())
            return ResponseEntity.badRequest().body("Error cancelling reservation");

        return ResponseEntity.ok().body(result);
    }


}

package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateFlightReservationDTO;
import com.roberdev.gestionturismo.dto.FlightReservationDTO;
import com.roberdev.gestionturismo.service.IFlightReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
            @ApiResponse(responseCode = "200", description = "Flight reservation created"),
            @ApiResponse(responseCode = "400", description = "Error creating flight reservation"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Create a new flight reservation")
    public ResponseEntity<?> createFlightReservation(@RequestBody CreateFlightReservationDTO createFlightReservationDTO) {

        String result = flightReservationService.createFlightReservation(createFlightReservationDTO);
        if (result.contains("Error"))
            return ResponseEntity.badRequest().body(result);


        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/flight-booking/cancel/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight reservation cancelled"),
            @ApiResponse(responseCode = "400", description = "Flight reservation not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Cancel flight reservation")
    public ResponseEntity<String> cancelReservation(@PathVariable Long id) {

        String result = flightReservationService.cancelReservation(id);
        if (result.contains("Error"))
            return ResponseEntity.badRequest().body(result);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/flight-booking/all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "reservations found"),
            @ApiResponse(responseCode = "204", description = "No reservations found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Get flight reservations")
    public ResponseEntity<?> getReservations() {
        List<FlightReservationDTO> reservations = flightReservationService.getReservations();
        if (reservations.isEmpty())
            return ResponseEntity.noContent().build();


        return ResponseEntity.ok().body(reservations);
    }


}

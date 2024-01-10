package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateFlightDTO;
import com.roberdev.gestionturismo.dto.EditFlightDTO;
import com.roberdev.gestionturismo.dto.FlightDTO;
import com.roberdev.gestionturismo.service.IFlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agency")
public class FlightController {

    @Autowired
    IFlightService flightService;

    @PostMapping("/flights/new")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Flight created"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Error creating flight")
    })
    @Operation(summary = "Create a new flight")
    public ResponseEntity<?> createFlight(@RequestBody CreateFlightDTO createFlightDto) {

        FlightDTO flightDTO = flightService.createFlight(createFlightDto);

        if (flightDTO == null) {
            return ResponseEntity.badRequest().body("Error creating flight");
        }

        return ResponseEntity.ok().body(flightDTO);
    }


    @GetMapping("/flights")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flights found"),
            @ApiResponse(responseCode = "204", description = "No flights found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Get flights")
    public ResponseEntity<?> getFlights(@RequestParam(required = false) LocalDate date1,
                                        @RequestParam(required = false) LocalDate date2,
                                        @RequestParam(required = false) String origin,
                                        @RequestParam(required = false) String destination) {

        List<FlightDTO> flightsDTO;

        if (date1 != null && date2 != null && origin != null && destination != null) {
            flightsDTO = flightService.getFlightsByDateAndOriginAndDestination(date1, date2, origin, destination);
        } else {
            flightsDTO = flightService.getAllFlights();
        }

        if (flightsDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(flightsDTO);
    }

    @GetMapping("/flights/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight found"),
            @ApiResponse(responseCode = "204", description = "No flight found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Get flight by id")
    public ResponseEntity<?> getFlightById(@PathVariable Long id) {

        FlightDTO flightDTO = flightService.getFlightById(id);

        if (flightDTO == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(flightDTO);
    }


    @PatchMapping("/flights/edit/{flightNumber}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight updated"),
            @ApiResponse(responseCode = "400", description = "Error updating flight"),
            @ApiResponse(responseCode = "500", description = "Server error")

    })
    @Operation(summary = "Update flight by flight number")
    public ResponseEntity<?> updateFlightByFlightNumber(@PathVariable String flightNumber, @RequestBody Map<String, Object> updates) {

        FlightDTO flightDTO = flightService.editFlight(flightNumber, updates);

        if (flightDTO == null) {
            return ResponseEntity.badRequest().body("Error updating flight");
        }

        return ResponseEntity.ok().body(flightDTO);
    }

    @PutMapping("/flights/edit/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight edited"),
            @ApiResponse(responseCode = "400", description = "Error editing flight"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Update flight by id")
    public ResponseEntity<?> editFlight(@PathVariable Long id, @RequestBody EditFlightDTO editFlightDTO) {

        FlightDTO flightDTO = flightService.editFlightById(id, editFlightDTO);

        if (flightDTO == null) {
            return ResponseEntity.badRequest().body("Error editing flight");
        }

        return ResponseEntity.ok().body(flightDTO);
    }

    @PutMapping("/flights/delete/{flightNumber}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Flight status changed"),
            @ApiResponse(responseCode = "400", description = "Error ocurred when changing flight status"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Change flight status")
    public ResponseEntity<?> changeActiveStatus(@PathVariable String flightNumber, @RequestParam Boolean isActive) {

        FlightDTO flightDTO = flightService.changeActiveStatus(flightNumber, isActive);

        if (flightDTO == null) {
            return ResponseEntity.badRequest().body("Error ocurred when changing flight status");
        }

        return ResponseEntity.ok().body(flightDTO);
    }


}

package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateFlightDTO;
import com.roberdev.gestionturismo.dto.EditFlightDTO;
import com.roberdev.gestionturismo.dto.FlightDTO;
import com.roberdev.gestionturismo.service.IFlightService;
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
    public ResponseEntity<?> createFlight(@RequestBody CreateFlightDTO createFlightDto) {

        FlightDTO flightDTO = flightService.createFlight(createFlightDto);
        if (flightDTO == null) {
            return ResponseEntity.badRequest().body("Error al crear el vuelo");
        }

        return ResponseEntity.ok().body(flightDTO);
    }


    @GetMapping("/flights")
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
    public ResponseEntity<?> getFlightById(@PathVariable Long id) {

        FlightDTO flightDTO = flightService.getFlightById(id);

        if (flightDTO == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(flightDTO);
    }


    @PatchMapping("/flights/edit/{flightNumber}")
    public ResponseEntity<?> updateFlightByFlightNumber(@PathVariable String flightNumber, @RequestBody Map<String, Object> updates) {

        FlightDTO flightDTO = flightService.editFlight(flightNumber, updates);

        if (flightDTO == null) {
            return ResponseEntity.badRequest().body("Error al actualizar el vuelo");
        }

        return ResponseEntity.ok().body(flightDTO);
    }

    @PostMapping("/flights/edit/{id}")
    public ResponseEntity<?> editFlight(@PathVariable Long id, @RequestBody EditFlightDTO editFlightDTO) {

        FlightDTO flightDTO = flightService.editFlightById(id, editFlightDTO);

        if (flightDTO == null) {
            return ResponseEntity.badRequest().body("Flight not edited");
        }

        return ResponseEntity.ok().body(flightDTO);
    }

    @PostMapping("/flights/delete/{flightNumber}")
    public ResponseEntity<?> changeActiveStatus(@PathVariable String flightNumber, @RequestParam Boolean isActive) {

        FlightDTO flightDTO = flightService.changeActiveStatus(flightNumber, isActive);

        if (flightDTO == null) {
            return ResponseEntity.badRequest().body("Error al cambiar el estado del vuelo");
        }

        return ResponseEntity.ok().body(flightDTO);
    }


}

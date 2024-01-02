package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateFlightReservationDTO;
import com.roberdev.gestionturismo.dto.CreateHotelReservationDTO;
import com.roberdev.gestionturismo.service.IFlightReservationService;
import com.roberdev.gestionturismo.service.IFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agency")
public class FlightReservationController {

    @Autowired
    IFlightReservationService flightReservationService;

    @PostMapping("/flight-booking/new")
    public ResponseEntity<?> createFlightReservation(@RequestBody CreateFlightReservationDTO createFlightReservationDTO) {

        Double price = flightReservationService.createFlightReservation(createFlightReservationDTO);


        if (price == null) {
            return ResponseEntity.badRequest().body("Ha habido un error al crear la reserva");
        }

        return ResponseEntity.ok().body(price);
    }


}

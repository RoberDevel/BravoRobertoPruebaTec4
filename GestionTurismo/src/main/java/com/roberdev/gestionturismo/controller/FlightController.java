package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateFlightDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {


    public ResponseEntity<?> createFlight(@RequestBody CreateFlightDTO createFlightDto) {


        return null;
    }


}

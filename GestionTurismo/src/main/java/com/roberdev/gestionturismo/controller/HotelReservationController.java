package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateHotelReservationDTO;
import com.roberdev.gestionturismo.service.IHotelReservationService;
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
    public ResponseEntity<Double> createHotelReservation(@RequestBody CreateHotelReservationDTO createHotelReservationDTO) {

        Double totalPrice = hotelReservationService.createHotelReservation(createHotelReservationDTO);

        if (totalPrice == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(totalPrice);

    }


}

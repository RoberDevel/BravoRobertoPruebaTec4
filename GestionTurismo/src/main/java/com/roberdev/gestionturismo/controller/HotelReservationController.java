package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateReservationDTO;
import com.roberdev.gestionturismo.dto.HotelReservationDTO;
import com.roberdev.gestionturismo.model.HotelReservation;
import com.roberdev.gestionturismo.repository.HotelReservationRepository;
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
    public ResponseEntity<Double> createHotelReservation(@RequestBody CreateReservationDTO createReservationDTO) {

        Double totalPrice = hotelReservationService.createHotelReservation(createReservationDTO);

        if (totalPrice == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.ok(totalPrice);

    }


}

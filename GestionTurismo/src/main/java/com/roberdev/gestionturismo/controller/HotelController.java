package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateHotelDTO;
import com.roberdev.gestionturismo.dto.HotelDTO;
import com.roberdev.gestionturismo.service.IHotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agency")
public class HotelController {

    @Autowired
    private IHotelService hotelService;

    @PostMapping("/hotels/new")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Hotel created"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Error creating hotel"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Create a new hotel")
    public ResponseEntity<?> createHotel(@RequestBody CreateHotelDTO hotel) {

        HotelDTO hotelResult = hotelService.createHotel(hotel);

        if (hotelResult == null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating hotel");
        }

        return ResponseEntity.ok(hotelResult);
    }


    //borrado lógico con posibilidad de reactivación
    @PutMapping("/hotels/delete/{hotelCode}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel deleted"),
            @ApiResponse(responseCode = "400", description = "Error occurred when changing hotel status"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Delete a hotel", description = "Change the status hotel to active or not active by hotel code")
    public ResponseEntity<?> changeActiveStatus(@PathVariable String hotelCode, @RequestParam boolean isActive) {

        HotelDTO hotel = hotelService.changeActiveStatus(hotelCode, isActive);

        if (hotel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred when changing hotel status");
        }
        return ResponseEntity.ok(hotel);
    }

    @GetMapping("/hotels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotels found"),
            @ApiResponse(responseCode = "204", description = "No hotels found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Get hotels")
    public ResponseEntity<?> getHotels(
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate availableFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate availableTo,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") String city

    ) {

        if (availableFrom != null && availableTo != null && city != null) {

            if (hotelService.getHotelsByDateAndCity(availableFrom, availableTo, city).isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok(hotelService.getHotelsByDateAndCity(availableFrom, availableTo, city));
        } else {

            if (hotelService.getAllHotels().isEmpty()) {
                return ResponseEntity.noContent().build();
            }

            List<HotelDTO> hotels = hotelService.getAllHotels();
            if (hotels.isEmpty()) return ResponseEntity.noContent().build();

            return ResponseEntity.ok(hotelService.getAllHotels());
        }
    }


    @PutMapping("/hotels/edit/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel updated"),
            @ApiResponse(responseCode = "400", description = "Error updating hotel"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Update hotel by id")
    public ResponseEntity<?> updateHotelById(@PathVariable Long id,
                                             @RequestParam("name") String name,
                                             @RequestParam("city") String city) {
        HotelDTO updatedHotel = hotelService.editHotelById(id, name, city);

        if (updatedHotel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating hotel");
        }

        return ResponseEntity.ok(updatedHotel);
    }

    @PatchMapping("/hotels/edit/{hotelCode}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel updated"),
            @ApiResponse(responseCode = "400", description = "Error updating hotel"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Update hotel by hotel code")
    public ResponseEntity<?> updateHotelByHotelCode(@PathVariable String hotelCode, @RequestBody Map<String, Object> updates) {
        HotelDTO updatedHotel = hotelService.editHotel(hotelCode, updates);

        if (updatedHotel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating hotel");
        }

        return ResponseEntity.ok(updatedHotel);
    }

    @GetMapping("/hotels/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Hotel found"),
            @ApiResponse(responseCode = "400", description = "No hotel found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @Operation(summary = "Get hotel by id")
    public ResponseEntity<HotelDTO> getHotel(@PathVariable Long id) {
        HotelDTO hotel = hotelService.getHotelById(id);

        if (hotel == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(hotel);
    }


}

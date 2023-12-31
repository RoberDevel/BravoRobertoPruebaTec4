package com.roberdev.gestionturismo.controller;

import com.roberdev.gestionturismo.dto.CreateHotelDTO;
import com.roberdev.gestionturismo.dto.HotelDTO;
import com.roberdev.gestionturismo.model.Hotel;
import com.roberdev.gestionturismo.service.IHotelService;
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
    public ResponseEntity<?> createHotel(@RequestBody CreateHotelDTO hotel) {

        HotelDTO hotelResult = hotelService.createHotel(hotel);

        if (hotelResult == null) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el hotel");
        }

        return ResponseEntity.ok(hotelResult);
    }

    //borrado lógico con posibilidad de reactivación
    @PostMapping("/hotels/delete/{hotelCode}")
    public ResponseEntity<?> changeActiveStatus(@PathVariable String hotelCode, @RequestParam boolean isActive) {

        HotelDTO hotel = hotelService.changeActiveStatus(hotelCode, isActive);

        if (hotel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al cambiar el estado del hotel");
        }
        return ResponseEntity.ok(hotel);
    }

    @GetMapping("/hotels")
    public ResponseEntity<?> getHotels(
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate availableFrom,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate availableTo) {

        if (availableFrom != null && availableTo != null) {
            // Lógica para obtener hoteles en un rango de fechas y destino específico

            if (hotelService.getHotelsByDate(availableFrom, availableTo).isEmpty()) {
                return ResponseEntity.ok().body("No hay hoteles disponibles en las fechas seleccionadas");
            }

            return ResponseEntity.ok(hotelService.getHotelsByDate(availableFrom, availableTo));
        } else {
            // Lógica para obtener todos los hoteles
            if (hotelService.getAllHotels().isEmpty()) {
                return ResponseEntity.ok().body("No hay hoteles disponibles");
            }
            return ResponseEntity.ok(hotelService.getAllHotels());
        }
    }


    @PutMapping("hotels/edit/{id}")
    public ResponseEntity<HotelDTO> updateHotelById(@PathVariable Long id,
                                                    @RequestParam("name") String name,
                                                    @RequestParam("city") String city) {
        HotelDTO updatedHotel = hotelService.editHotelById(id, name, city);

        if (updatedHotel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(updatedHotel);
    }

    @PatchMapping("hotels/edit/{hotelCode}")
    public ResponseEntity<HotelDTO> updateHotelByHotelCode(@PathVariable String hotelCode, @RequestBody Map<String, Object> updates) {
        HotelDTO updatedHotel = hotelService.editHotel(hotelCode, updates);
        return ResponseEntity.ok(updatedHotel);
    }

    @GetMapping("/hotels/{id}")
    public ResponseEntity<HotelDTO> getHotel(@PathVariable Long id) {
        HotelDTO hotel = hotelService.getHotelById(id);

        if (hotel == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.ok(hotel);
    }


}

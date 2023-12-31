package com.roberdev.gestionturismo.dto;

import com.roberdev.gestionturismo.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {

    private Room.RoomType roomType;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    private Double pricePerNight;
    private List<HotelReservationDTO> reservations;


}

package com.roberdev.gestionturismo.dto;

import com.roberdev.gestionturismo.model.Room;
import com.roberdev.gestionturismo.model.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomDTO {
    private RoomType roomType;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    private Double pricePerNight;
}

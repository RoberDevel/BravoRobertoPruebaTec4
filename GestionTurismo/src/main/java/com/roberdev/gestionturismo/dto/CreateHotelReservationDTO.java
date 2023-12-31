package com.roberdev.gestionturismo.dto;

import com.roberdev.gestionturismo.model.enums.RoomType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateHotelReservationDTO {

    private String hotelCode;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private RoomType roomType;
    private List<PersonDTO> guests;
}

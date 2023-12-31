package com.roberdev.gestionturismo.dto;

import com.roberdev.gestionturismo.model.Person;
import com.roberdev.gestionturismo.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationDTO {

    private String hotelCode;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Room.RoomType roomType;
    private List<PersonDTO> guests;
}

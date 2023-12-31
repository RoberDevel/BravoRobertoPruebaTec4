package com.roberdev.gestionturismo.dto;

import com.roberdev.gestionturismo.model.Person;
import com.roberdev.gestionturismo.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelReservationDTO {

    private String hotelCode;
    private Integer guestsNumber;
    private Integer nights;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Room.RoomType roomType;
    private Double totalPrice;
    private List<PersonDTO> guests = new ArrayList<>();


}
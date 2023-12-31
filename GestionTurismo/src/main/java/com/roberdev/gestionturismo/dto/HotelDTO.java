package com.roberdev.gestionturismo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {

    private String hotelCode;
    private String name;
    private String city;
    private Boolean isActive;
    private List<LocalDate> statusChangeDates = new ArrayList<>();
    private List<RoomDTO> rooms = new ArrayList<>();
}

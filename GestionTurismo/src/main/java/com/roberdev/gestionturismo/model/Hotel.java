package com.roberdev.gestionturismo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hotelCode;
    private String name;
    private String city;
    private Boolean isActive;
    private List<LocalDate> statusChangeDates = new ArrayList<>();


    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms = new ArrayList<>();


}



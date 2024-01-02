package com.roberdev.gestionturismo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;


    @ManyToMany(mappedBy = "passengers", cascade = CascadeType.ALL)
    private List<FlightReservation> flightReservations = new ArrayList<>();


    @ManyToMany(mappedBy = "guests", cascade = CascadeType.ALL)
    private List<HotelReservation> roomReservations = new ArrayList<>();


}

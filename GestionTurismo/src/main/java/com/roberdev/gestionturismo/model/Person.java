package com.roberdev.gestionturismo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String dni;


    @ManyToMany(mappedBy = "passengers", cascade = CascadeType.ALL)
    private List<FlightReservation> flightReservations = new ArrayList<>();


    @ManyToMany(mappedBy = "guests", cascade = CascadeType.ALL)
    private List<HotelReservation> roomReservations = new ArrayList<>();


}

package com.roberdev.gestionturismo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.roberdev.gestionturismo.model.enums.FlightSeatType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class FlightReservation {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String flightToCode;
    private String flightBackCode;

    private Integer peopleQ;

    private FlightSeatType seatTypeFlightTo;
    private FlightSeatType seatTypeFlightBack;

    private Double totalPrice;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "flight_reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> passengers = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "flight_to_id")
    private Flight flightTo;

    @ManyToOne
    @JoinColumn(name = "flight_back_id")
    private Flight flightBack;

}

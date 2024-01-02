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
    private LocalDate dateFlightTo;
    private LocalDate dateFlightBack;
    private Integer passengersNumber;
    private FlightSeatType seatTypeFlightTo;
    private FlightSeatType seatTypeFlightBack;

    private Double totalPrice;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "flight_reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> passengers = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "flight_reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id"))
    private List<Flight> flights = new ArrayList<>();


}

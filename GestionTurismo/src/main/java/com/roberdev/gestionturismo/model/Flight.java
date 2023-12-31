package com.roberdev.gestionturismo.model;

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
public class Flight {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    private String flightNumber;

    private String origin = "";
    private String destination = "";

    @Enumerated(EnumType.STRING)
    private FlightSeatType seatType;

    private Double seatPrice;

    private Boolean isFull;

    private LocalDate date;

    private Integer totalSeats;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<FlightReservation> flightReservations = new ArrayList<>();
}


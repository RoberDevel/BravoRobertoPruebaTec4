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

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "seat_type")
    @Column(name = "price")
    @CollectionTable(name = "seat_type_prices", joinColumns = @JoinColumn(name = "flight_id"))
    private Map<FlightSeatType, Double> seatTypePrices = new EnumMap<>(FlightSeatType.class);

    private Boolean isFull;
    private LocalDate date;
    private Integer totalSeats;
    private Boolean isActive;
    private List<LocalDate> statusChangeDates = new ArrayList<>();


    @ManyToMany(mappedBy = "flights", cascade = CascadeType.ALL)
    private List<FlightReservation> flightReservations = new ArrayList<>();
}


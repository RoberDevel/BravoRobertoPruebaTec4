package com.roberdev.gestionturismo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Room.RoomType roomType;
    private LocalDate availableFrom;
    private LocalDate availableTo;
    private Double pricePerNight;

/*
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
*/
    // Getter personalizado para incluir solo la información deseada del hotel
  /*  public Long getHotelId() {
        return hotel != null ? hotel.getId() : null;
    }

    public String getHotelCode() {
        return hotel != null ? hotel.getHotelCode() : null;
    }

    public String getHotelName() {
        return hotel != null ? hotel.getName() : null;
    }
*/

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<HotelReservation> hotelReservations = new ArrayList<>();

    public enum RoomType {
        SINGLE, DOUBLE, TRIPLE, MULTIPLE
    }
}

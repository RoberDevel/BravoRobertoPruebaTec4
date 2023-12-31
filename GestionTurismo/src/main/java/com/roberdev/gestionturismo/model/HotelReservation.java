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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hotelCode;
    private Integer guestsNumber;
    private Integer nights;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Room.RoomType roomType;
    private Double totalPrice;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "hotel_reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    List<Person> guests = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


}

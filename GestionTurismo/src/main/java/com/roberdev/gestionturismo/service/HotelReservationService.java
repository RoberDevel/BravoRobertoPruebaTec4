package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.converter.HotelReservationConverter;
import com.roberdev.gestionturismo.dto.CreateReservationDTO;
import com.roberdev.gestionturismo.dto.HotelReservationDTO;
import com.roberdev.gestionturismo.model.Hotel;
import com.roberdev.gestionturismo.model.HotelReservation;
import com.roberdev.gestionturismo.model.Person;
import com.roberdev.gestionturismo.model.Room;
import com.roberdev.gestionturismo.repository.HotelRepository;
import com.roberdev.gestionturismo.repository.HotelReservationRepository;
import com.roberdev.gestionturismo.repository.PersonRepository;
import com.roberdev.gestionturismo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HotelReservationService implements IHotelReservationService {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    HotelReservationRepository hotelReservationRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    HotelReservationConverter hotelReservationConverter;

    @Override
    public Double createHotelReservation(CreateReservationDTO createReservationDTO) {

        if (createReservationDTO.getCheckInDate().isAfter(createReservationDTO.getCheckOutDate()) ||
                createReservationDTO.getCheckInDate().isBefore(LocalDate.now()) || createReservationDTO.getCheckInDate().equals(createReservationDTO.getCheckOutDate())) {
            return null;
        }
        Hotel hotel = hotelRepository.findByHotelCode(createReservationDTO.getHotelCode());
        if (hotel == null) {
            return null;
        }

        if (hotel.getIsActive() == false) {
            return null;
        }

        if (createReservationDTO.getGuests().isEmpty()) {
            return null;
        }

        HotelReservation hotelReservation = hotelReservationConverter.convertCreateReservationDTOToHotelReservation(createReservationDTO);

        // Buscar habitación disponible, realizar reserva y obtener precio total
        Double totalPrice = checkAvailability(hotel, hotelReservation);
        if (totalPrice != null) return totalPrice;
        return null; // No se encontró una habitación disponible
    }


    // Buscar habitación disponible,
    private Double checkAvailability(Hotel hotel, HotelReservation hotelReservation) {
        for (Room room : hotel.getRooms()) {
            if (room.getRoomType().equals(hotelReservation.getRoomType()) &&
                    room.getAvailableFrom().isBefore(hotelReservation.getCheckInDate()) &&
                    room.getAvailableTo().isAfter(hotelReservation.getCheckOutDate())) {

                boolean isOverlapping = room.getHotelReservations().stream()
                        .anyMatch(reservation ->
                                !(hotelReservation.getCheckOutDate().isBefore(reservation.getCheckInDate()) ||
                                        hotelReservation.getCheckInDate().isAfter(reservation.getCheckOutDate())));

                Double totalPrice = processHotelReservation(hotel, hotelReservation, room, isOverlapping);
                if (totalPrice != null) return totalPrice;
            }
        }
        return null;
    }

    private Double processHotelReservation(Hotel hotel, HotelReservation hotelReservation, Room room, boolean isOverlapping) {
        if (!isOverlapping) {

            long totalDays = ChronoUnit.DAYS.between(hotelReservation.getCheckInDate(), hotelReservation.getCheckOutDate());

            Double totalPrice = totalDays * room.getPricePerNight();

            // Crear huéspedes y actualizar reserva
            List<Person> guests = createGuests(hotelReservation);
            hotelReservation.setGuests(guests);
            hotelReservation.setGuestsNumber(guests.size());
            hotelReservation.setNights((int) totalDays);
            hotelReservation.setCheckInDate(hotelReservation.getCheckInDate());
            hotelReservation.setCheckOutDate(hotelReservation.getCheckOutDate());
            hotelReservation.setTotalPrice(totalPrice);
            hotelReservation.setRoomType(room.getRoomType());
            hotelReservation.setHotelCode(hotel.getHotelCode());
            hotelReservation.setRoom(room);
            // Guardar reserva
            hotelReservationRepository.save(hotelReservation);

            return totalPrice;
        }
        return null;
    }

    private List<Person> createGuests(HotelReservation hotelReservation) {
        List<Person> guests = new ArrayList<>();
        
        hotelReservation.getGuests().forEach(person -> {
            Person guest = new Person();
            guest.setName(person.getName());
            guest.setLastName(person.getLastName());
            guest.setEmail(person.getEmail());
            guest.setPhone(person.getPhone());
            personRepository.save(guest);
            guests.add(guest);
        });
        return guests;
    }


}

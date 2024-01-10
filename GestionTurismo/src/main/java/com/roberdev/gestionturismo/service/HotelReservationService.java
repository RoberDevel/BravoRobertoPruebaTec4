package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.converter.HotelReservationConverter;
import com.roberdev.gestionturismo.converter.PersonConverter;
import com.roberdev.gestionturismo.dto.CreateFlightReservationDTO;
import com.roberdev.gestionturismo.dto.CreateHotelReservationDTO;
import com.roberdev.gestionturismo.dto.HotelReservationDTO;
import com.roberdev.gestionturismo.model.*;
import com.roberdev.gestionturismo.repository.HotelRepository;
import com.roberdev.gestionturismo.repository.HotelReservationRepository;
import com.roberdev.gestionturismo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelReservationService implements IHotelReservationService {

    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    HotelReservationRepository hotelReservationRepository;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonConverter personConverter;
    @Autowired
    HotelReservationConverter hotelReservationConverter;

    @Override
    public String createHotelReservation(CreateHotelReservationDTO createHotelReservationDTO) {

        if (createHotelReservationDTO.getCheckInDate().isAfter(createHotelReservationDTO.getCheckOutDate()) ||
                createHotelReservationDTO.getCheckInDate().isBefore(LocalDate.now()) || createHotelReservationDTO.getCheckInDate().equals(createHotelReservationDTO.getCheckOutDate())) {
            return "Error, invalid dates";
        }
        Hotel hotel = hotelRepository.findByHotelCode(createHotelReservationDTO.getHotelCode());
        if (hotel == null) {
            return "Error, hotel not found";
        }

        if (hotel.getIsActive() == false) {
            return "Error, hotel is not active";
        }

        if (createHotelReservationDTO.getGuests().isEmpty()) {
            return "Error, guests list is empty";
        }


        HotelReservation hotelReservation = hotelReservationConverter.convertCreateReservationDTOToHotelReservation(createHotelReservationDTO);

        // Buscar habitación disponible, realizar reserva y obtener precio total
        Room room = checkAvailability(hotel, hotelReservation);
        if (room == null) {
            return "Error, there is no room available";
        }
        if (checkNumberOfGuestsAndTypeOfRoom(hotelReservation)) {
            return "Error, number of guests " + createHotelReservationDTO.getGuests().size() + " is not permitted for a " + createHotelReservationDTO.getRoomType().toString().toLowerCase() + " room";
        }

        Double totalPrice = processHotelReservation(hotel, hotelReservation, room);

        return "Reservation created, total price: " + totalPrice;

    }

    private boolean checkNumberOfGuestsAndTypeOfRoom(HotelReservation hotelReservation) {

        switch (hotelReservation.getRoomType()) {

            case SINGLE:
                if (hotelReservation.getGuests().size() > 1) {
                    return true;
                }
                break;
            case DOUBLE:
                if (hotelReservation.getGuests().size() > 2) {
                    return true;
                }
                break;
            case TRIPLE:
                if (hotelReservation.getGuests().size() > 3) {
                    return true;
                }
                break;
            default:

                break;

        }

        return false;

    }

    @Override
    public String cancelReservation(Long id) {

        HotelReservation hotelReservation = hotelReservationRepository.findById(id).orElse(null);

        if (hotelReservation == null) {
            return "Error, reservation not found";
        }
        hotelReservationRepository.delete(hotelReservation);
        return "Reservation cancelled";


    }

    @Override
    public List<HotelReservationDTO> getReservations() {

        List<HotelReservationDTO> hotelReservationDTOS = hotelReservationConverter.convertToDTOList(hotelReservationRepository.findAll());


        return hotelReservationDTOS;
    }

    private Room checkAvailability(Hotel hotel, HotelReservation hotelReservation) {

        List<Room> rooms = hotel.getRooms().stream()
                .filter(room -> room.getRoomType().equals(hotelReservation.getRoomType()))
                .filter(room -> room.getAvailableFrom().isBefore(hotelReservation.getCheckInDate()) &&
                        room.getAvailableTo().isAfter(hotelReservation.getCheckOutDate()))
                .collect(Collectors.toList());


        Room room1 = rooms.stream()
                .filter(r -> r.getHotelReservations().stream()
                        .noneMatch(reservation ->
                                !(reservation.getCheckOutDate().isBefore(hotelReservation.getCheckInDate()) ||
                                        reservation.getCheckInDate().isAfter(hotelReservation.getCheckOutDate()))
                        )
                )
                .findFirst()
                .orElse(null);

        return room1;

    }

    private Double processHotelReservation(Hotel hotel, HotelReservation hotelReservation, Room room) {


        long totalDays = ChronoUnit.DAYS.between(hotelReservation.getCheckInDate(), hotelReservation.getCheckOutDate());

        Double totalPrice = totalDays * room.getPricePerNight();

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

        hotelReservationRepository.save(hotelReservation);

        return totalPrice;

    }

    private List<Person> createGuests(HotelReservation hotelReservation) {
        List<Person> guests = new ArrayList<>();

        for (Person per : hotelReservation.getGuests()) {
            Person person = personRepository.findByNameAndLastNameAndDni(per.getName(), per.getLastName(), per.getDni())
                    .orElseGet(() -> {
                        Person newPerson = new Person();
                        newPerson.setName(per.getName());
                        newPerson.setLastName(per.getLastName());
                        newPerson.setEmail(per.getEmail());
                        newPerson.setPhone(per.getPhone());
                        newPerson.setDni(per.getDni());
                        return personRepository.save(newPerson);
                    });
            guests.add(person);
        }
        return guests;

    }

}

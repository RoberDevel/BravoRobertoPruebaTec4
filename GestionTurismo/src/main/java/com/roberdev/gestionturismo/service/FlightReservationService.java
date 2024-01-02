package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.converter.FlightReservationConverter;
import com.roberdev.gestionturismo.dto.CreateFlightReservationDTO;
import com.roberdev.gestionturismo.model.Flight;
import com.roberdev.gestionturismo.model.FlightReservation;
import com.roberdev.gestionturismo.model.Person;
import com.roberdev.gestionturismo.model.enums.FlightSeatType;
import com.roberdev.gestionturismo.repository.FlightRepository;
import com.roberdev.gestionturismo.repository.FlightReservationRepository;
import com.roberdev.gestionturismo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FlightReservationService implements IFlightReservationService {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    FlightReservationRepository flightReservationRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    FlightReservationConverter flightReservationConverter;

    @Override
    public Double createFlightReservation(CreateFlightReservationDTO createFlightReservationDTO) {
        if (createFlightReservationDTO.getDateFlightTo().isAfter(Optional.ofNullable(createFlightReservationDTO.getDateFlightBack()).orElse(createFlightReservationDTO.getDateFlightTo()))) {
            return null;
        }

        FlightReservation flightReservation = flightReservationConverter.convertCreateReservationDTOToFlightReservation(createFlightReservationDTO);
        List<Person> passengers = createPassengers(createFlightReservationDTO);
        flightReservation.setPassengers(passengers);

        if (!processFlight(createFlightReservationDTO.getFlightToCode(), createFlightReservationDTO.getDateFlightTo(), createFlightReservationDTO.getSeatTypeFlightTo(), flightReservation, createFlightReservationDTO.getPassengers().size())) {
            return null;
        }

        String flightBackCode = createFlightReservationDTO.getFlightBackCode();
        if (!flightBackCode.isBlank() && !processFlight(flightBackCode, createFlightReservationDTO.getDateFlightBack(), createFlightReservationDTO.getSeatTypeFlightBack(), flightReservation, createFlightReservationDTO.getPassengers().size())) {
            return null;
        }

        flightReservation.setPassengersNumber(flightReservation.getPassengers().size());

        Flight flightTo = flightRepository.findByFlightNumberAndDate(createFlightReservationDTO.getFlightToCode(), createFlightReservationDTO.getDateFlightTo());

        flightTo.getFlightReservations().add(flightReservation);


        flightReservationRepository.save(flightReservation);
        flightRepository.save(flightTo);
        Flight flightBack = flightRepository.findByFlightNumberAndDate(createFlightReservationDTO.getFlightBackCode(), createFlightReservationDTO.getDateFlightBack());
        if (flightBack != null) {
            flightBack.getFlightReservations().add(flightReservation);
            flightRepository.save(flightBack);
        }
        return flightReservation.getTotalPrice();
    }


    private List<Person> createPassengers(CreateFlightReservationDTO createFlightReservationDTO) {
        List<Person> passengers = new ArrayList<>();

        createFlightReservationDTO.getPassengers().forEach(person -> {
            Person guest = new Person();
            guest.setName(person.getName());
            guest.setLastName(person.getLastName());
            guest.setEmail(person.getEmail());
            guest.setPhone(person.getPhone());
            personRepository.save(guest);
            passengers.add(guest);
        });
        return passengers;
    }

    private boolean processFlight(String flightCode, LocalDate flightDate, FlightSeatType seatType, FlightReservation flightReservation, int numberOfPassengers) {
        Flight flight = flightRepository.findByFlightNumberAndDate(flightCode, flightDate);
        if (flight == null || !flight.getIsActive() || flight.getIsFull() || flight.getTotalSeats() < numberOfPassengers) {
            return false;
        }

        flight.setTotalSeats(flight.getTotalSeats() - numberOfPassengers);
        if (flight.getTotalSeats() == 0) {
            flight.setIsFull(true);
        }

        flightReservation.getFlights().add(flight);
        flightReservation.setTotalPrice(calculateTotalPrice(flightReservation.getTotalPrice(), seatType, flight));

        return true;
    }

    private Double calculateTotalPrice(Double currentTotal, FlightSeatType seatType, Flight flight) {
        Double priceForSeatType = flight.getSeatTypePrices().getOrDefault(seatType, 0.0);
        return Optional.ofNullable(currentTotal).orElse(0.0) + priceForSeatType;
    }

}

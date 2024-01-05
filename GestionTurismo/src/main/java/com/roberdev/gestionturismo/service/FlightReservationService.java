package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.converter.FlightReservationConverter;
import com.roberdev.gestionturismo.converter.PersonConverter;
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
import java.util.stream.Collectors;

@Service
public class FlightReservationService implements IFlightReservationService {

    @Autowired
    FlightRepository flightRepository;

    @Autowired
    FlightReservationRepository flightReservationRepository;

    @Autowired
    PersonRepository personRepository;
    @Autowired
    PersonConverter personConverter;

    @Autowired
    FlightReservationConverter flightReservationConverter;

    @Override
    public Double createFlightReservation(CreateFlightReservationDTO createFlightReservationDTO) {
        if (checkIncorrectDatesAndReservationExist(createFlightReservationDTO)) return null;

        FlightReservation flightReservation = flightReservationConverter.convertCreateReservationDTOToFlightReservation(createFlightReservationDTO);
        List<Person> passengers = createPassengers(createFlightReservationDTO);
        flightReservation.setPassengers(passengers);


        if (!createFlightReservationDTO.getFlightBackCode().isBlank() && !processFlight(createFlightReservationDTO, flightReservation)) {
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

    private boolean checkIncorrectDatesAndReservationExist(CreateFlightReservationDTO createFlightReservationDTO) {
        if (createFlightReservationDTO.getDateFlightTo().isAfter(Optional.ofNullable(createFlightReservationDTO.getDateFlightBack()).orElse(createFlightReservationDTO.getDateFlightTo()))) {
            return true;
        }

        if (reservationExists(createFlightReservationDTO)) {
            return true;
        }
        return false;
    }

    private boolean reservationExists(CreateFlightReservationDTO createFlightReservationDTO) {

        List<FlightReservation> flightReservations = flightReservationRepository.findAll();
        return flightReservations.stream()
                .filter(reservation -> reservation.getFlightToCode().equals(createFlightReservationDTO.getFlightToCode()))
                .anyMatch(reservation -> {
                    List<Long> existingPassengerIds = reservation.getPassengers().stream()
                            .map(Person::getId)
                            .collect(Collectors.toList());

                    return createFlightReservationDTO.getPassengers().stream()
                            .map(personDTO -> personConverter.convertToEntity(personDTO).getId())
                            .anyMatch(passengerId -> !existingPassengerIds.contains(passengerId));
                });
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

    private boolean processFlight(CreateFlightReservationDTO createFlightReservationDTO, FlightReservation flightReservation) {
        Flight flight = checkNullFlightOrIsActiveOrIsFull(createFlightReservationDTO);

        if (flight == null) return false;

        flight.setTotalSeats(flight.getTotalSeats() - createFlightReservationDTO.getPassengers().size());
        if (flight.getTotalSeats() == 0) {
            flight.setIsFull(true);
        }

        flightReservation.getFlights().add(flight);
        flightReservation.setTotalPrice(calculateTotalPrice(flightReservation.getTotalPrice(), createFlightReservationDTO.getSeatTypeFlightTo(), flight));

        return true;
    }

    private Flight checkNullFlightOrIsActiveOrIsFull(CreateFlightReservationDTO createFlightReservationDTO) {
        Flight flight = flightRepository.findByFlightNumberAndDate(createFlightReservationDTO.getFlightToCode(), createFlightReservationDTO.getDateFlightTo());
        if (flight == null || !flight.getIsActive() || flight.getIsFull() || flight.getTotalSeats() < createFlightReservationDTO.getPassengers().size()) {
            return null;
        }
        return flight;
    }

    private Double calculateTotalPrice(Double currentTotal, FlightSeatType seatType, Flight flight) {
        Double priceForSeatType = flight.getSeatTypePrices().getOrDefault(seatType, 0.0);
        return Optional.ofNullable(currentTotal).orElse(0.0) + priceForSeatType;
    }

    @Override
    public List<FlightReservation> getReservations() {

        List<FlightReservation> flightReservations = flightReservationRepository.findAll();

        return flightReservations;
    }

    @Override
    public String cancelReservation(Long id) {

        flightReservationRepository.deleteById(id);

        return "Reservation cancelled";
    }


}

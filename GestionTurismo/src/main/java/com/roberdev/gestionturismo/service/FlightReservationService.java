package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.converter.FlightReservationConverter;
import com.roberdev.gestionturismo.converter.PersonConverter;
import com.roberdev.gestionturismo.dto.CreateFlightReservationDTO;
import com.roberdev.gestionturismo.dto.FlightReservationDTO;
import com.roberdev.gestionturismo.dto.PersonDTO;
import com.roberdev.gestionturismo.model.Flight;
import com.roberdev.gestionturismo.model.FlightReservation;
import com.roberdev.gestionturismo.model.Person;
import com.roberdev.gestionturismo.repository.FlightRepository;
import com.roberdev.gestionturismo.repository.FlightReservationRepository;
import com.roberdev.gestionturismo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    public String createFlightReservation(CreateFlightReservationDTO createFlightReservationDTO) {
        //se comprueba el vuelo de ida
        if (!checkNullFlightOrIsActive(createFlightReservationDTO.getFlightToCode())) {
            return "Error, flight " + createFlightReservationDTO.getFlightToCode() + " does not exist";
        }
        if (isFull(createFlightReservationDTO.getPassengers().size(), createFlightReservationDTO.getFlightToCode())) {
            return "Error, only " + flightRepository.findByFlightNumber(createFlightReservationDTO.getFlightToCode()).getTotalSeats() + " seats available";
        }

        if (!checkDates(createFlightReservationDTO.getDateFlightTo(), createFlightReservationDTO.getFlightToCode())) {
            return "Error, incorrect dates";
        }
        if (reservationExists(createFlightReservationDTO)) {
            return "Error, reservation already exists";
        }

        Flight flightTo = flightRepository.findByFlightNumberAndDate(createFlightReservationDTO.getFlightToCode(), createFlightReservationDTO.getDateFlightTo());

        FlightReservation flightReservation = flightReservationConverter.convertCreateReservationDTOToFlightReservation(createFlightReservationDTO);
        List<Person> passengers = createPassengers(createFlightReservationDTO);
        flightReservation.setPassengers(passengers);
        flightReservation.setFlightToCode(createFlightReservationDTO.getFlightToCode());
        flightReservation.setDateFlightTo(createFlightReservationDTO.getDateFlightTo());
        flightReservation.setSeatTypeFlightTo(createFlightReservationDTO.getSeatTypeFlightTo());
        flightReservation.setPassengersNumber(passengers.size());

        flightTo.setTotalSeats(flightTo.getTotalSeats() - createFlightReservationDTO.getPassengers().size());

        flightRepository.save(flightTo);

        Double totalPrice = 0.0;
        for (Person person : passengers) {
            totalPrice += flightTo.getSeatTypePrices().get(createFlightReservationDTO.getSeatTypeFlightTo());
        }

        //Se comprueba el vuelo de vuelta
        if (createFlightReservationDTO.getFlightBackCode().isBlank() || !checkNullFlightOrIsActive(createFlightReservationDTO.getFlightBackCode()) || isFull(createFlightReservationDTO.getPassengers().size(), createFlightReservationDTO.getFlightBackCode()) || !checkDates(createFlightReservationDTO.getDateFlightBack(), createFlightReservationDTO.getFlightBackCode())) {

            flightReservation.setFlightBackCode("");
            flightReservation.setDateFlightBack(null);
            flightReservation.setSeatTypeFlightBack(null);
            flightReservation.setTotalPrice(totalPrice);
            flightReservationRepository.save(flightReservation);
            return "Reservation created, total price: " + totalPrice;
        }

        flightReservation.setFlightBackCode(createFlightReservationDTO.getFlightBackCode());
        flightReservation.setDateFlightBack(createFlightReservationDTO.getDateFlightBack());
        flightReservation.setSeatTypeFlightBack(createFlightReservationDTO.getSeatTypeFlightBack());

        for (Person person : passengers) {
            totalPrice += flightTo.getSeatTypePrices().get(createFlightReservationDTO.getSeatTypeFlightBack());
        }

        flightReservation.setTotalPrice(totalPrice);
        Flight flightBack = flightRepository.findByFlightNumberAndDate(createFlightReservationDTO.getFlightBackCode(), createFlightReservationDTO.getDateFlightBack());
        flightBack.setTotalSeats(flightBack.getTotalSeats() - createFlightReservationDTO.getPassengers().size());
        flightRepository.save(flightBack);
        flightReservationRepository.save(flightReservation);

        return "Reservation created, total price: " + totalPrice;

    }


    private boolean checkDates(LocalDate date, String flightNumber) {

        if (flightRepository.findByFlightNumberAndDate(flightNumber, date) == null) {
            return false;
        }
        return true;
    }

    private boolean reservationExists(CreateFlightReservationDTO createFlightReservationDTO) {

        List<FlightReservation> flightReservations = flightReservationRepository.findAll();
        Set<String> newReservationDnis = createFlightReservationDTO.getPassengers().stream()
                .map(personDTO -> personConverter.convertToEntity(personDTO).getDni())
                .collect(Collectors.toSet());

        return flightReservations.stream()
                .filter(reservation -> reservation.getFlightToCode().equals(createFlightReservationDTO.getFlightToCode()))
                .flatMap(reservation -> reservation.getPassengers().stream())
                .map(Person::getDni)
                .anyMatch(newReservationDnis::contains);
    }

    private List<Person> createPassengers(CreateFlightReservationDTO createFlightReservationDTO) {
        List<Person> passengers = new ArrayList<>();

        for (PersonDTO personDTO : createFlightReservationDTO.getPassengers()) {
            Person person = personRepository.findByNameAndLastNameAndDni(personDTO.getName(), personDTO.getLastName(), personDTO.getDni())
                    .orElseGet(() -> {
                        Person newPerson = new Person();
                        newPerson.setName(personDTO.getName());
                        newPerson.setLastName(personDTO.getLastName());
                        newPerson.setEmail(personDTO.getEmail());
                        newPerson.setPhone(personDTO.getPhone());
                        newPerson.setDni(personDTO.getDni());
                        return personRepository.save(newPerson);
                    });
            passengers.add(person);
        }
        return passengers;
    }

    private Boolean checkNullFlightOrIsActive(String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber);
        if (flight == null || !flight.getIsActive() || flight.getIsFull()
        ) {
            return false;
        }
        return true;
    }

    private Boolean isFull(Integer numberPassengers, String flightNumber) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber);
        if (flight == null) {
            return true;
        }

        if (flight.getTotalSeats() < numberPassengers) {
            return true;
        }
        return false;
    }

    @Override
    public List<FlightReservationDTO> getReservations() {

        List<FlightReservationDTO> flightReservations = flightReservationConverter.convertToDTOList(flightReservationRepository.findAll());

        return flightReservations;
    }

    @Override
    public String cancelReservation(Long id) {

        FlightReservation flightReservation = flightReservationRepository.findById(id).orElse(null);
        if (flightReservation == null) {
            return "Reservation not found";
        }
        String flightToCode = flightReservation.getFlightToCode();


        Flight flight = flightRepository.findByFlightNumber(flightToCode);
        flight.setTotalSeats(flight.getTotalSeats() + flightReservation.getPassengersNumber());
        flightRepository.save(flight);
        String flightBackCode = flightReservation.getFlightBackCode();
        if (!flightBackCode.isBlank()) {
            Flight flightBack = flightRepository.findByFlightNumber(flightBackCode);
            flightBack.setTotalSeats(flightBack.getTotalSeats() + flightReservation.getPassengersNumber());
            flightRepository.save(flightBack);
        }

        flightReservationRepository.deleteById(id);

        return "Reservation cancelled";
    }


}

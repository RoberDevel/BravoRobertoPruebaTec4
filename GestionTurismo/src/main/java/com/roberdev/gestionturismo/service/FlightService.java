package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.converter.FlightConverter;
import com.roberdev.gestionturismo.converter.FlightReservationConverter;
import com.roberdev.gestionturismo.converter.PersonConverter;
import com.roberdev.gestionturismo.dto.*;
import com.roberdev.gestionturismo.model.Flight;
import com.roberdev.gestionturismo.model.FlightReservation;
import com.roberdev.gestionturismo.model.HotelReservation;
import com.roberdev.gestionturismo.model.Person;
import com.roberdev.gestionturismo.model.enums.FlightSeatType;
import com.roberdev.gestionturismo.repository.FlightRepository;
import com.roberdev.gestionturismo.repository.FlightReservationRepository;
import com.roberdev.gestionturismo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class FlightService implements IFlightService {

    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private FlightConverter flightConverter;

    @Autowired
    private FlightReservationConverter flightReservationConverter;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private FlightReservationRepository flightReservationRepository;

    @Autowired
    private PersonConverter personConverter;


    @Override
    public FlightDTO createFlight(CreateFlightDTO createFlightDTO) {

        if (createFlightDTO.getDate().isBefore(LocalDate.now())) {

            return null;
        }

        if (flightRepository.findByOriginAndDestinationAndDateAndTotalSeats(createFlightDTO.getOrigin(), createFlightDTO.getDestination(), createFlightDTO.getDate(), createFlightDTO.getTotalSeats()) != null) {

            return null;
        }

        FlightDTO flightDTO = flightConverter.convertCreateFlightDTOToFlightDTO(createFlightDTO);
        Flight flight = flightConverter.convertToEntity(flightDTO);
        flight.setFlightNumber(codFlightGenerator(flight.getOrigin(), flight.getDestination(), flight.getDate()));
        System.out.println(flight.getFlightNumber());
        flight.setIsFull(false);
        flight.getStatusChangeDates().add(LocalDate.now());
        flightRepository.save(flight);
        flightDTO = flightConverter.convertToDTO(flight);

        return flightDTO;
    }

    @Override
    public List<FlightDTO> getAllFlights() {

        List<FlightDTO> flightsDTO = flightRepository.findAll().stream().map(flightConverter::convertToDTO).toList();
        return flightsDTO;
    }

    @Override
    public List<FlightDTO> getFlightsByDateAndOriginAndDestination(LocalDate date1, LocalDate date2, String origin, String destination) {

        if (date1.isAfter(date2)) {
            return null;
        }

        List<FlightDTO> flightsDTO = flightRepository.findByDateBetweenAndOriginAndDestination(date1, date2, origin, destination).stream().map(flightConverter::convertToDTO).toList();

        return flightsDTO;
    }

    @Override
    public FlightDTO editFlight(String flightNumber, Map<String, Object> updates) {

        Flight flight = flightRepository.findByFlightNumber(flightNumber);
        if (flight == null) {
            return null;
        }

        updates.forEach((key, value) -> {
            switch (key) {
                case "origin":
                    flight.setOrigin((String) value);
                    break;
                case "destination":
                    flight.setDestination((String) value);
                    break;
                case "totalSeats":
                    flight.setTotalSeats((Integer) value);
                    break;
                case "isActive":
                    flight.setIsActive((Boolean) value);
                    break;
                case "seatTypePrices":
                    // Asegúrate de que value es del tipo correcto antes de realizar el casting
                    if (value instanceof Map) {
                        Map<?, ?> rawMap = (Map<?, ?>) value;
                        Map<FlightSeatType, Double> seatTypePrices = new EnumMap<>(FlightSeatType.class);
                        rawMap.forEach((k, v) -> {
                            if (k instanceof FlightSeatType && v instanceof Number) {
                                seatTypePrices.put((FlightSeatType) k, ((Number) v).doubleValue());
                            }
                        });
                        flight.setSeatTypePrices(seatTypePrices);
                    }
                    break;
                default:
                    break;
            }
        });
        flightRepository.save(flight);

        return flightConverter.convertToDTO(flight);
    }

    @Override
    public FlightDTO editFlightById(Long id, EditFlightDTO editFlightDTO) {

        if (editFlightDTO.getDate().isBefore(LocalDate.now())) {

            return null;
        }
        Flight flight = flightRepository.findById(id).orElse(null);
        if (flight == null) {

            return null;
        }
        flight.setOrigin(editFlightDTO.getOrigin());
        flight.setDestination(editFlightDTO.getDestination());
        flight.setSeatTypePrices(editFlightDTO.getSeatTypePrices());
        flight.setDate(editFlightDTO.getDate());
        flight.setTotalSeats(editFlightDTO.getTotalSeats());
        flight.setIsActive(editFlightDTO.getIsActive());
        flight.setFlightNumber(codFlightGenerator(flight.getOrigin(), flight.getDestination(), flight.getDate()));
        flightRepository.save(flight);
        FlightDTO flightDTO = flightConverter.convertToDTO(flight);

        return flightDTO;
    }

    @Override
    public FlightDTO getFlightById(Long id) {
        Flight flight = flightRepository.findById(id).orElse(null);
        if (flight == null) {
            return null;
        }
        return flightConverter.convertToDTO(flight);
    }

    @Override
    public FlightDTO changeActiveStatus(String flightNumber, boolean isActive) {
        Flight flight = flightRepository.findByFlightNumber(flightNumber);

        if (flight == null) {
            return null;
        }
        if (isActive == flight.getIsActive() || !LocalDate.now().isAfter(flight.getStatusChangeDates().get(flight.getStatusChangeDates().size() - 1))) {
            return null;
        }

        flight.setIsActive(isActive);
        flight.getStatusChangeDates().add(LocalDate.now());
        return flightConverter.convertToDTO(flightRepository.save(flight));
    }

    /*
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
    */
    private String codFlightGenerator(String origin, String destination, LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMM");
        String codDate = date.format(formatter);

        String codFlight1 = extractCode(origin);
        String codFlight2 = extractCode(destination);

        // Obtener el siguiente número autoincremental y formatearlo
        Long nextNum = flightRepository.count() + 1;
        String formatNum = String.format("%06d", nextNum);

        // Combinar las letras y el número
        return codFlight1 + codFlight2 + "-" + codDate + formatNum;
    }

    private String extractCode(String location) {
        String[] words = location.split(" ");
        if (words.length >= 2) {
            // Tomar las iniciales de las dos primeras palabras
            return (words[0].substring(0, 1) + words[1].substring(0, 1)).toUpperCase();
        } else if (words.length == 1 && words[0].length() >= 2) {
            // Tomar las dos primeras letras de la única palabra
            return words[0].substring(0, 2).toUpperCase();
        } else {
            // En caso de que no haya palabras o la palabra sea muy corta, asignar un valor predeterminado
            return "NA";
        }
    }

}

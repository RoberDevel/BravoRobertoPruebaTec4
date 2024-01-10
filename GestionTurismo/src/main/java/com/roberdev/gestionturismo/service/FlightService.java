package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.converter.FlightConverter;
import com.roberdev.gestionturismo.converter.FlightReservationConverter;
import com.roberdev.gestionturismo.converter.PersonConverter;
import com.roberdev.gestionturismo.dto.*;
import com.roberdev.gestionturismo.model.Flight;
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
        flight.setIsFull(false);
        flight.getStatusChangeDates().add(LocalDate.now());
        flightRepository.save(flight);
        flightDTO = flightConverter.convertToDTO(flight);

        return flightDTO;
    }

    @Override
    public List<FlightDTO> getAllFlights() {

        List<FlightDTO> flightsDTO = flightRepository.findAll().stream().map(flightConverter::convertToDTO).sorted(Comparator.comparing(FlightDTO::getDate)).toList();
        return flightsDTO;
    }

    @Override
    public List<FlightDTO> getFlightsByDateAndOriginAndDestination(LocalDate date1, LocalDate date2, String origin, String destination) {

        if (date1.isAfter(date2)) {
            return null;
        }

        List<FlightDTO> flightsDTO = flightRepository.findByDateBetweenAndOriginAndDestination(date1, date2, origin, destination).stream().map(flightConverter::convertToDTO).sorted(Comparator.comparing(FlightDTO::getDate)).toList();

        return flightsDTO;
    }

    @Override
    public FlightDTO editFlight(String flightNumber, Map<String, Object> updates) {

        Flight flight = flightRepository.findByFlightNumber(flightNumber);
        if (flight == null) {
            return null;
        }

        updateFlightFromSwitch(updates, flight);

        flightRepository.save(flight);

        return flightConverter.convertToDTO(flight);
    }

    private static void updateFlightFromSwitch(Map<String, Object> updates, Flight flight) {
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
    }

    @Override
    public FlightDTO editFlightById(Long id, EditFlightDTO editFlightDTO) {

        if (checkDateIsBeforeNow(editFlightDTO)) return null;
        Flight flight = flightRepository.findById(id).orElse(null);
        if (flight == null) {

            return null;
        }

        updateFlightDetails(flight, editFlightDTO);
        flightRepository.save(flight);

        return flightConverter.convertToDTO(flight);
    }

    private static boolean checkDateIsBeforeNow(EditFlightDTO editFlightDTO) {
        if (editFlightDTO.getDate().isBefore(LocalDate.now())) {

            return true;
        }
        return false;
    }

    private void updateFlightDetails(Flight flight, EditFlightDTO editFlightDTO) {
        flight.setOrigin(editFlightDTO.getOrigin());
        flight.setDestination(editFlightDTO.getDestination());
        flight.setSeatTypePrices(editFlightDTO.getSeatTypePrices());
        flight.setDate(editFlightDTO.getDate());
        flight.setTotalSeats(editFlightDTO.getTotalSeats());
        flight.setIsActive(editFlightDTO.getIsActive());
        flight.setFlightNumber(codFlightGenerator(flight.getOrigin(), flight.getDestination(), flight.getDate()));
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

        if (checkNullAndReservationsEmptyAndIsActive(isActive, flight)) return null;

        flight.setIsActive(isActive);
        flight.getStatusChangeDates().add(LocalDate.now());
        return flightConverter.convertToDTO(flightRepository.save(flight));
    }

    private static boolean checkNullAndReservationsEmptyAndIsActive(boolean isActive, Flight flight) {
        if (flight == null) {
            return true;
        }

        if (!flight.getFlightReservations().isEmpty()) {
            return true;
        }


        if (isActive == flight.getIsActive()
            //|| !LocalDate.now().isAfter(flight.getStatusChangeDates().get(flight.getStatusChangeDates().size() - 1))
        ) {
            return true;
        }
        return false;
    }


    private String codFlightGenerator(String origin, String destination, LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMM");
        String codDate = date.format(formatter);

        String codFlight1 = extractCode(origin);
        String codFlight2 = extractCode(destination);

        Long nextNum = flightRepository.count() + 1;
        String formatNum = String.format("%06d", nextNum);

        return codFlight1 + codFlight2 + "-" + codDate + formatNum;
    }

    private static String extractCode(String location) {
        String[] words = location.split(" ");
        if (words.length >= 2) {

            return (words[0].substring(0, 1) + words[1].substring(0, 1)).toUpperCase();
        } else if (words.length == 1 && words[0].length() >= 2) {

            return words[0].substring(0, 2).toUpperCase();
        } else {

            return "NA";
        }
    }

}

package com.roberdev.gestionturismo.converter;

import com.roberdev.gestionturismo.dto.CreateFlightDTO;
import com.roberdev.gestionturismo.dto.EditFlightDTO;
import com.roberdev.gestionturismo.dto.FlightDTO;
import com.roberdev.gestionturismo.dto.FlightReservationDTO;
import com.roberdev.gestionturismo.model.Flight;
import com.roberdev.gestionturismo.model.enums.FlightSeatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FlightConverter implements Converter<Flight, FlightDTO> {

    @Autowired
    FlightReservationConverter flightReservationConverter;


    @Override
    public FlightDTO convertToDTO(Flight flight) {
        if (flight == null) {
            return null;
        }

        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setFlightNumber(flight.getFlightNumber());
      
        flightDTO.setOrigin(flight.getOrigin());
        flightDTO.setDestination(flight.getDestination());
        flightDTO.setIsFull(flight.getIsFull());
        flightDTO.setDate(flight.getDate());
        flightDTO.setTotalSeats(flight.getTotalSeats());
        flightDTO.setIsActive(flight.getIsActive());
        flightDTO.setStatusChangeDates(flight.getStatusChangeDates());
        if (flight.getSeatTypePrices() != null) {
            flightDTO.setSeatTypePrices(new EnumMap<>(flight.getSeatTypePrices()));
        }

        if (flight.getFlightReservations() != null) {
            List<FlightReservationDTO> reservationDTOs = flight.getFlightReservations().stream()
                    .map(flightReservation -> flightReservationConverter.convertToDTO(flightReservation))
                    .collect(Collectors.toList());
            flightDTO.setFlightReservations(reservationDTOs);
        }

        return flightDTO;
    }


    @Override
    public Flight convertToEntity(FlightDTO flightDTO) {
        if (flightDTO == null) {
            return null;
        }

        Flight flight = new Flight();
        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setOrigin(flightDTO.getOrigin());
        flight.setDestination(flightDTO.getDestination());
        flight.setIsFull(flightDTO.getIsFull());
        flight.setDate(flightDTO.getDate());
        flight.setTotalSeats(flightDTO.getTotalSeats());
        flight.setIsActive(flightDTO.getIsActive());
        flight.setStatusChangeDates(flightDTO.getStatusChangeDates());
        // Convertir los precios de los tipos de asientos
        if (flightDTO.getSeatTypePrices() != null) {
            flight.setSeatTypePrices(new EnumMap<>(flightDTO.getSeatTypePrices()));
        }

        // Convertir las reservas de vuelo
        if (flightDTO.getFlightReservations() != null) {
            flight.setFlightReservations(flightDTO.getFlightReservations().stream()
                    .map(flightReservationConverter::convertToEntity)
                    .collect(Collectors.toList()));
        }

        return flight;
    }


    private Map<FlightSeatType, String> seatTypePrices;

    public FlightDTO convertCreateFlightDTOToFlightDTO(CreateFlightDTO createFlightDTO) {

        if (createFlightDTO == null) {
            return null;
        }

        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setOrigin(createFlightDTO.getOrigin());
        flightDTO.setDestination(createFlightDTO.getDestination());
        if (createFlightDTO.getSeatTypePrices() != null) {
            flightDTO.setSeatTypePrices(new EnumMap<>(createFlightDTO.getSeatTypePrices()));
        }
        flightDTO.setIsActive(createFlightDTO.getIsActive());
        flightDTO.setDate(createFlightDTO.getDate());
        flightDTO.setTotalSeats(createFlightDTO.getTotalSeats());


        return flightDTO;
    }


    public FlightDTO convertEditFlightDTOToFlightDTO(EditFlightDTO editFlightDTO) {

        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setOrigin(editFlightDTO.getOrigin());
        flightDTO.setDestination(editFlightDTO.getDestination());
        if (editFlightDTO.getSeatTypePrices() != null) {
            flightDTO.setSeatTypePrices(new EnumMap<>(editFlightDTO.getSeatTypePrices()));
        }
        flightDTO.setIsActive(editFlightDTO.getIsActive());
        flightDTO.setDate(editFlightDTO.getDate());
        flightDTO.setTotalSeats(editFlightDTO.getTotalSeats());
        return flightDTO;
    }
}
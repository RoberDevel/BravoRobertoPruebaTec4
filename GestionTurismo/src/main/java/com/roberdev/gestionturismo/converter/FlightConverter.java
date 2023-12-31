package com.roberdev.gestionturismo.converter;

import com.roberdev.gestionturismo.dto.CreateFlightDTO;
import com.roberdev.gestionturismo.dto.FlightDTO;
import com.roberdev.gestionturismo.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlightConverter implements Converter<Flight, FlightDTO> {


    @Autowired
    FlightReservationConverter flightReservationConverter;


    @Override
    public FlightDTO convertToDTO(Flight entity) {

        if (entity == null) {
            return null;
        }

        FlightDTO flightDto = new FlightDTO();
        flightDto.setFlightNumber(entity.getFlightNumber());
        flightDto.setOrigin(entity.getOrigin());
        flightDto.setDestination(entity.getDestination());
        flightDto.setSeatType(entity.getSeatType());
        flightDto.setSeatPrice(entity.getSeatPrice());
        flightDto.setIsFull(entity.getIsFull());
        flightDto.setDate(entity.getDate());
        flightDto.setTotalSeats(entity.getTotalSeats());
        if (entity.getFlightReservations() != null) {
            flightDto.setFlightReservations(entity.getFlightReservations().stream()
                    .map(flightReservationConverter::convertToDTO).toList());
        }

        return flightDto;
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
        flight.setSeatType(flightDTO.getSeatType());
        flight.setSeatPrice(flightDTO.getSeatPrice());
        flight.setIsFull(flightDTO.getIsFull());
        flight.setDate(flightDTO.getDate());
        flight.setTotalSeats(flightDTO.getTotalSeats());
        flight.setFlightReservations(flightDTO.getFlightReservations().stream()
                .map(flightReservationConverter::convertToEntity).toList());
        return flight;
    }

    public FlightDTO convertCreateFlightDTOToFlightDTO(CreateFlightDTO createFlightDTO) {

        if (createFlightDTO == null) {
            return null;
        }

        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setOrigin(createFlightDTO.getOrigin());
        flightDTO.setDestination(createFlightDTO.getDestination());
        flightDTO.setSeatType(createFlightDTO.getSeatType());
        flightDTO.setDate(createFlightDTO.getDate());
        flightDTO.setTotalSeats(createFlightDTO.getTotalSeats());

        return flightDTO;
    }


}
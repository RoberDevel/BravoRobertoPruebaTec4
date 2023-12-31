package com.roberdev.gestionturismo.converter;

import com.roberdev.gestionturismo.dto.FlightReservationDTO;
import com.roberdev.gestionturismo.dto.PersonDTO;
import com.roberdev.gestionturismo.model.Flight;
import com.roberdev.gestionturismo.model.FlightReservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FlightReservationConverter implements Converter<FlightReservation, FlightReservationDTO> {
    @Autowired
    private PersonConverter personConverter;

    @Override
    public FlightReservationDTO convertToDTO(FlightReservation flightReservation) {

        if (flightReservation == null) {
            return null;
        }

        List<PersonDTO> personDTOList = new ArrayList<>();
        if (flightReservation.getPassengers() != null) {
            personDTOList = flightReservation.getPassengers().stream().map(personConverter::convertToDTO).toList();
        }

        FlightReservationDTO flightReservationDTO = new FlightReservationDTO();

        flightReservationDTO.setFlightToCode(flightReservation.getFlightToCode());
        flightReservationDTO.setFlightBackCode(flightReservation.getFlightBackCode());
        flightReservationDTO.setDateFlightTo(flightReservation.getDateFlightTo());
        flightReservationDTO.setDateFlightBack(flightReservation.getDateFlightBack());
        flightReservationDTO.setPassengersNumber(flightReservation.getPassengersNumber());
        flightReservationDTO.setSeatTypeFlightTo(flightReservation.getSeatTypeFlightTo());
        flightReservationDTO.setSeatTypeFlightBack(flightReservation.getSeatTypeFlightBack());
        flightReservationDTO.setTotalPrice(flightReservation.getTotalPrice());
        flightReservationDTO.setPassengers(personDTOList);

        return null;
    }

    @Override
    public FlightReservation convertToEntity(FlightReservationDTO flightReservationDTO) {

        if (flightReservationDTO == null) {
            return null;
        }

        FlightReservation flightReservation = new FlightReservation();
        flightReservation.setFlightToCode(flightReservationDTO.getFlightToCode());
        flightReservation.setFlightBackCode(flightReservationDTO.getFlightBackCode());
        flightReservation.setDateFlightTo(flightReservationDTO.getDateFlightTo());
        flightReservation.setDateFlightBack(flightReservationDTO.getDateFlightBack());
        flightReservation.setPassengersNumber(flightReservationDTO.getPassengersNumber());
        flightReservation.setSeatTypeFlightTo(flightReservationDTO.getSeatTypeFlightTo());
        flightReservation.setSeatTypeFlightBack(flightReservationDTO.getSeatTypeFlightBack());
        flightReservation.setTotalPrice(flightReservationDTO.getTotalPrice());
        if (flightReservationDTO.getPassengers() != null) {
            flightReservation.setPassengers(flightReservationDTO.getPassengers().stream().map(personConverter::convertToEntity).toList());
        }

        return null;
    }
}

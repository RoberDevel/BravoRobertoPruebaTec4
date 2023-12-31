package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.converter.FlightConverter;
import com.roberdev.gestionturismo.dto.CreateFlightDTO;
import com.roberdev.gestionturismo.dto.EditFlightDTO;
import com.roberdev.gestionturismo.dto.FlightDTO;
import com.roberdev.gestionturismo.model.Flight;
import com.roberdev.gestionturismo.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class FlightService implements IFlightService {

    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    FlightConverter flightConverter;

    @Override
    public FlightDTO createFlight(CreateFlightDTO createFlightDTO) {

        if (createFlightDTO.getDate().isBefore(LocalDate.now())) {
            return null;
        }

        if (flightRepository.findByOriginAndDestinationAndDateAndTotalSeats(createFlightDTO.getOrigin(), createFlightDTO.getDestination(), createFlightDTO.getDate(), createFlightDTO.getTotalSeats()) == null) {
            return null;
        }

        FlightDTO flightDTO = flightConverter.convertCreateFlightDTOToFlightDTO(createFlightDTO);
        Flight flight = flightConverter.convertToEntity(flightDTO);
        flight.setFlightNumber(codFlightGenerator(flight.getOrigin(), flight.getDestination(), flight.getDate()));
        flight.setOrigin(flight.getOrigin());
        flight.setDestination(flight.getDestination());
        flight.setSeatType(null);


        return null;
    }

    @Override
    public List<FlightDTO> getAllFlights() {
        return null;
    }

    @Override
    public List<FlightDTO> getFlightsByDateAndOriginAndDestination(String date, String date2, String origin, String destination) {
        return null;
    }

    @Override
    public FlightDTO editFlight(String flightCode, Map<String, Object> updates) {
        return null;
    }

    @Override
    public FlightDTO editFlightById(Long id, EditFlightDTO editFlightDTO) {
        return null;
    }

    @Override
    public FlightDTO getFlightById(Long id) {
        return null;
    }

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


    /*private String codFlightGenerator(String origin, String destination, LocalDate date) {
        String[] words1 = origin.split(" ");
        String[] words2 = origin.split(" ");
        String codFlight1;
        String codFlight2;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMM");
        String codDate = date.format(formatter);

        if (words1.length >= 2) {
            // Tomar las iniciales de las dos primeras palabras
            codFlight1 = (words1[0].substring(0, 1) + words1[1].substring(0, 1)).toUpperCase();
        } else if (words1.length == 1) {
            // Tomar las dos primeras letras de la única palabra
            codFlight1 = words1[0].substring(0, 2).toUpperCase();
        } else {
            // En caso de que no haya palabras, asignar un valor predeterminado
            codFlight1 = "NA";
        }

        if (words2.length >= 2) {
            // Tomar las iniciales de las dos primeras palabras
            codFlight2 = (words1[0].substring(0, 1) + words1[1].substring(0, 1)).toUpperCase();
        } else if (words2.length == 1) {
            // Tomar las dos primeras letras de la única palabra
            codFlight2 = words2[0].substring(0, 2).toUpperCase();
        } else {
            // En caso de que no haya palabras, asignar un valor predeterminado
            codFlight2 = "NA";
        }


        // Obtener el siguiente número autoincremental y formatearlo
        Long nextNum = flightRepository.count() + 1;
        String formatNum = String.format("%06d", nextNum);

        // Combinar las letras y el número
        return codFlight1 + codFlight2 + "-" + codDate + formatNum;
    }*/


}

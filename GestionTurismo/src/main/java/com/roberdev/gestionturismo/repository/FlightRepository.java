package com.roberdev.gestionturismo.repository;

import com.roberdev.gestionturismo.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Flight findByFlightCode(String flightCode);

    Flight findByOriginAndDestinationAndDateAndTotalSeats(String origin, String destination, LocalDate date, Integer totalSeats);


}

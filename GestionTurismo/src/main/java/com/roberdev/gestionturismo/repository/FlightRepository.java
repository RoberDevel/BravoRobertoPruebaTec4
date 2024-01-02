package com.roberdev.gestionturismo.repository;

import com.roberdev.gestionturismo.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    Flight findByFlightNumber(String flightCode);

    Flight findByOriginAndDestinationAndDateAndTotalSeats(String origin, String destination, LocalDate date, Integer totalSeats);

    List<Flight> findByDateBetweenAndOriginAndDestination(LocalDate date1, LocalDate date2, String origin, String destination);

    Flight findByFlightNumberAndDate(String flightToCode, LocalDate date);
}


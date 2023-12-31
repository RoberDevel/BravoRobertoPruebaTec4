package com.roberdev.gestionturismo.repository;

import com.roberdev.gestionturismo.model.FlightReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightReservationRepository extends JpaRepository<FlightReservation, Long> {
}

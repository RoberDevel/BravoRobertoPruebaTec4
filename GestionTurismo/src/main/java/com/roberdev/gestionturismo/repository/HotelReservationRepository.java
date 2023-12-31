package com.roberdev.gestionturismo.repository;

import com.roberdev.gestionturismo.model.HotelReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelReservationRepository extends JpaRepository<HotelReservation, Long> {
}

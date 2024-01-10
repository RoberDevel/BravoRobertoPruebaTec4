package com.roberdev.gestionturismo.repository;

import com.roberdev.gestionturismo.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    //List<Hotel> findByAvailableFromLessThanEqualAndAvailableToGreaterThanEqual(LocalDate dateEnd, LocalDate dateStart);

    Hotel findByNameAndCity(String name, String city);

    Hotel findByHotelCode(String hotelCode);

    @Query("SELECT DISTINCT h FROM Hotel h JOIN h.rooms hab WHERE h.city = :city AND hab.availableFrom <= :endDate AND hab.availableTo >= :startDate")
    List<Hotel> findHotelsByAvailabilityAndCity(LocalDate startDate, LocalDate endDate, String city);


}

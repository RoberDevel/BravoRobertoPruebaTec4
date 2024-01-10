package com.roberdev.gestionturismo.repository;

import com.roberdev.gestionturismo.model.HotelReservation;
import com.roberdev.gestionturismo.model.enums.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface HotelReservationRepository extends JpaRepository<HotelReservation, Long> {


    @Query("SELECT COUNT(r) > 0 FROM HotelReservation r WHERE r.hotelCode = :hotelCode AND r.roomType = :roomType AND " +
            "((:checkInDate BETWEEN r.checkInDate AND r.checkOutDate) OR " +
            "(:checkOutDate BETWEEN r.checkInDate AND r.checkOutDate) OR " +
            "(r.checkInDate BETWEEN :checkInDate AND :checkOutDate) OR " +
            "(r.checkOutDate BETWEEN :checkInDate AND :checkOutDate))")
    boolean existsByHotelCodeAndRoomTypeAndOverlapDates(
            @Param("hotelCode") String hotelCode,
            @Param("roomType") RoomType roomType,
            @Param("checkInDate") LocalDate checkInDate,
            @Param("checkOutDate") LocalDate checkOutDate);

}

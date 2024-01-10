package com.roberdev.gestionturismo.service;

import com.roberdev.gestionturismo.converter.HotelConverter;
import com.roberdev.gestionturismo.converter.RoomConverter;
import com.roberdev.gestionturismo.dto.CreateHotelDTO;
import com.roberdev.gestionturismo.dto.HotelDTO;
import com.roberdev.gestionturismo.model.Hotel;
import com.roberdev.gestionturismo.model.Room;
import com.roberdev.gestionturismo.repository.HotelRepository;
import com.roberdev.gestionturismo.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class HotelService implements IHotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelConverter hotelConverter;

    @Autowired
    RoomConverter roomConverter;

    @Autowired
    private RoomRepository roomRepository;

    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Override
    public HotelDTO createHotel(CreateHotelDTO createHotelDTO) {

        if (hotelRepository.findByNameAndCity(createHotelDTO.getName(), createHotelDTO.getCity()) != null) {
            return null;
        }


        HotelDTO hotelDTO = hotelConverter.convertCreateHotelDTOToHotelDTO(createHotelDTO);
        Hotel hotel = hotelConverter.convertToEntity(hotelDTO);
        hotel.setHotelCode(codHotelGenerator(hotel.getName()));
        hotel.setIsActive(true);
        hotel.getStatusChangeDates().add(LocalDate.now());

        hotelRepository.save(hotel);
        hotelDTO = hotelConverter.convertToDTO(hotel);

        return hotelDTO;
    }

    @Override
    public List<HotelDTO> getAllHotels() {

        List<Hotel> hotels = hotelRepository.findAll();

        return hotels.stream().map(hotelConverter::convertToDTO).toList();
    }

    @Override
    public List<HotelDTO> getHotelsByDateAndCity(LocalDate date, LocalDate date2, String city) {

        List<Hotel> hotels = hotelRepository.findHotelsByAvailabilityAndCity(date, date2, city);

        return hotels.stream().map(hotel -> {
            HotelDTO hotelDTO = hotelConverter.convertToDTO(hotel);
            List<Room> filteredRooms = hotel.getRooms().stream()
                    .filter(room -> (date.isBefore(room.getAvailableTo()) && date.isAfter(room.getAvailableFrom())) || (date2.isBefore(room.getAvailableTo()) && date2.isAfter(room.getAvailableFrom())))
                    .collect(Collectors.toList());
            hotelDTO.setRooms(roomConverter.convertToDTOList(filteredRooms));
            return hotelDTO;
        }).collect(Collectors.toList());

    }

    public HotelDTO editHotel(String hotelCode, Map<String, Object> updates) {
        Hotel hotel = hotelRepository.findByHotelCode(hotelCode);

        if (hotel == null) {
            return null;
        }

        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    hotel.setName((String) value);
                    break;
                case "city":
                    hotel.setCity((String) value);
                    break;
                default:
                    break;
            }
        });
        return hotelConverter.convertToDTO(hotelRepository.save(hotel));
    }

    @Override
    public HotelDTO editHotelById(Long id, String name, String city) {

        Hotel hotel = hotelRepository.findById(id).orElse(null);

        if (hotel != null) {
            hotel.setName(name);
            hotel.setCity(city);
            hotelRepository.save(hotel);
            return hotelConverter.convertToDTO(hotel);
        }

        return null;
    }

    @Override
    public HotelDTO changeActiveStatus(String hotelCode, boolean isActive) {
        Hotel hotel = hotelRepository.findByHotelCode(hotelCode);

        if (hotel == null) {
            return null;
        }


        if (!hotel.getRooms().stream().noneMatch(room -> !room.getHotelReservations().isEmpty())) {
            return null;
        }


        if (isActive != hotel.getIsActive()
            // || LocalDate.now().isAfter(hotel.getStatusChangeDates().get(hotel.getStatusChangeDates().size() - 1))
        ) {
            hotel.setIsActive(isActive);
            hotel.getStatusChangeDates().add(LocalDate.now());
        }
        hotelRepository.save(hotel);

        return hotelConverter.convertToDTO(hotel);
    }


    @Override
    public HotelDTO getHotelById(Long id) {
        Optional<Hotel> hotel = hotelRepository.findById(id);

        return hotel.map(value -> hotelConverter.convertToDTO(value)).orElse(null);

    }

    protected String codHotelGenerator(String name) {
        String[] words = name.split(" ");
        String codHotel;

        if (words.length >= 2) {

            codHotel = (words[0].substring(0, 1) + words[1].substring(0, 1)).toUpperCase();
        } else if (words.length == 1) {

            codHotel = words[0].substring(0, 2).toUpperCase();
        } else {

            codHotel = "NA";
        }

        Long nextNum = hotelRepository.count() + 1;
        String formatNum = String.format("%07d", nextNum);

        return codHotel + "-" + formatNum;
    }


}

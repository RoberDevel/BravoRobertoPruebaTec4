package com.roberdev.gestionturismo.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.roberdev.gestionturismo.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;


@ExtendWith(MockitoExtension.class)
public class HotelServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService hotelService;

    @BeforeEach
    void setUp() {
        hotelService = new HotelService(hotelRepository);
    }

    @Test
    public void testCodHotelGenerator_WithTwoWords_ShouldGenerateCorrectCode() {

        String name = "Sunset Resort";
        long nextNum = 10L;
        when(hotelRepository.count()).thenReturn(nextNum - 1);

        String code = hotelService.codHotelGenerator(name);


        String expectedCode = "SR-" + String.format("%07d", nextNum);
        assertEquals(expectedCode, code);
    }

    @Test
    public void testCodHotelGenerator_WithOneWord_ShouldGenerateCorrectCode() {

        String name = "Sunset";
        long nextNum = 5L;
        when(hotelRepository.count()).thenReturn(nextNum - 1);

        String code = hotelService.codHotelGenerator(name);

        String expectedCode = "SU-" + String.format("%07d", nextNum);
        assertEquals(expectedCode, code);
    }

}

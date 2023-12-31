package com.roberdev.gestionturismo.converter;

import com.roberdev.gestionturismo.dto.PersonDTO;
import com.roberdev.gestionturismo.model.HotelReservation;
import com.roberdev.gestionturismo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonConverter implements Converter<Person, PersonDTO> {


    @Override
    public PersonDTO convertToDTO(Person person) {

        if (person == null) {
            return null;
        }
        PersonDTO personDTO = new PersonDTO(person.getName(), person.getLastName(), person.getEmail(), person.getPhone());
        return personDTO;
    }

    @Override
    public Person convertToEntity(PersonDTO personDTO) {

        if (personDTO == null) {
            return null;
        }

        Person person = new Person();
        person.setName(personDTO.getName());
        person.setLastName(personDTO.getLastName());
        person.setEmail(personDTO.getEmail());
        person.setPhone(personDTO.getPhone());

        //TODO: AÑADIR LA LISTA DE FLIGHTRESERVATION, CREO

        return person;
    }
}

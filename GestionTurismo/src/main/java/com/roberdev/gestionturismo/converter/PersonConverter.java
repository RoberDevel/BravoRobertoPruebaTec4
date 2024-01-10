package com.roberdev.gestionturismo.converter;

import com.roberdev.gestionturismo.dto.PersonDTO;
import com.roberdev.gestionturismo.model.Person;
import org.springframework.stereotype.Component;


@Component
public class PersonConverter implements Converter<Person, PersonDTO> {


    @Override
    public PersonDTO convertToDTO(Person person) {

        if (person == null) {
            return null;
        }
        PersonDTO personDTO = new PersonDTO(person.getName(), person.getLastName(), person.getEmail(), person.getPhone(), person.getDni());
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
        person.setDni(personDTO.getDni());

        return person;
    }
}

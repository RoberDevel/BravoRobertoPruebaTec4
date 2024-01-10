package com.roberdev.gestionturismo.repository;

import com.roberdev.gestionturismo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByNameAndLastNameAndDni(String name, String lastName, String dni);


}

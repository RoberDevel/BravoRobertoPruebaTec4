package com.roberdev.gestionturismo.repository;

import com.roberdev.gestionturismo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}

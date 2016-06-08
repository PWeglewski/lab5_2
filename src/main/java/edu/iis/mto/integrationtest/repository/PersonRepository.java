package edu.iis.mto.integrationtest.repository;


import edu.iis.mto.integrationtest.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {

    public List<Person> findByFirstNameLike(String firstName);

}

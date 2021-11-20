 package com.postcodelotterie.assessment.repository;


import java.util.List;
import java.util.Optional;

import com.postcodelotterie.assessment.model.Person;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

 @Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByLastName(String q);
    Person findBydob(String dob);
    Optional<Person> findById(Long id);

     @Modifying
     @Query("delete from Person p where p.id = ?1")
     void deleteById(Long id);

     boolean existsById(Long id);
 }
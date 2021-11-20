package com.postcodelotterie.assessment.service;
import com.postcodelotterie.assessment.model.Person;
import com.postcodelotterie.assessment.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/*
public interface PersonService {

    List<Person> findAll();

    List<Person> search(String q);

    Person findOne(Long id);

    @Transactional
    void save(Person person);

    void delete(Long id);
}*/
@Service
public class PersonService{
    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return (List<Person>) personRepository.findAll();
    }

    public List<Person> search(String q) {
        return personRepository.findByLastName(q);
    }


    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }


    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    public boolean existsById(Long id){
        return personRepository.existsById(id);
    }
}
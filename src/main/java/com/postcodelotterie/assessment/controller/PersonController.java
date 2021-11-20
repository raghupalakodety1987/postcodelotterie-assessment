package com.postcodelotterie.assessment.controller;

import com.postcodelotterie.assessment.ResponseMessage;
import com.postcodelotterie.assessment.model.Person;
import com.postcodelotterie.assessment.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class PersonController {

    private static final String SAVE_SUCCESSFUL = "Succesfully Saved";
    private static final String COULD_NOT_UPLOAD = "Could not save the person";
    private static final String DELETED_THE_PERSON = "Person deleted" ;
    private static final String FAILED_DELETION = "Person could not be deleted";
    private static final String USER_FOUND = "Person found";

    @Autowired
    private PersonService personService;

    @GetMapping("/person")
    public ResponseEntity<Object> getPersons() {
        try {
            List<Person> persons = personService.findAll();

            if (persons.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<Object>(persons, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/person/create")
    public ResponseEntity<ResponseMessage> createPerson(@RequestBody Person person) {
        String message = "";
        try{
            personService.save(person);
            message = SAVE_SUCCESSFUL;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        }
        catch (Exception e){
            message =  COULD_NOT_UPLOAD  + "!" + e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @RequestMapping("/person/{id}")
/*  @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
          method = RequestMethod.GET,
          value = "/schema")*/
    public ResponseEntity<Optional<Person>> getPersonById(@PathVariable Long id) {
        String message = "";
        try {
            Optional<Person> person = personService.findById(id);
                return ResponseEntity.status(HttpStatus.OK).body(person);
            }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/person/save")
    public ResponseEntity<ResponseMessage> save(@RequestBody Person person) {
            String message = "";
            try{
                personService.save(person);
                message = SAVE_SUCCESSFUL;
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            }
            catch (Exception e){
                message =  COULD_NOT_UPLOAD  + "!" + e.getMessage();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
    }

    @DeleteMapping("/person/{id}/delete")
    public ResponseEntity<ResponseMessage> deletePersonById(@PathVariable Long id) {
        String message = "";
        try {
            personService.deleteById(id);
            message = DELETED_THE_PERSON;
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
         /*   if(personService.findOne(id) == null){
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            }*/
         /*   else {
                message = FAILED_DELETION;
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(FAILED_DELETION));

            }*/
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/person/search")
    public ResponseEntity<Object> search(@RequestParam("s") String s) {
        try {
            List<Person> persons = personService.findAll();

            Optional<Person> personFound = persons.stream().filter(person -> (person.getFirstName().contains(s) || person.getLastName().contains(s))).findFirst();

            return new ResponseEntity<Object>(personFound.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
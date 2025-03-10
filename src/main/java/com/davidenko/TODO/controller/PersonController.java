package com.davidenko.TODO.controller;

import com.davidenko.TODO.model.DTO.PersonDTO;
import com.davidenko.TODO.model.Person;
import com.davidenko.TODO.service.PersonService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<PersonDTO> personList = personService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(personList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable("id") Long id){
       PersonDTO personDTO = personService.getPersonById(id);
        return ResponseEntity.status(HttpStatus.OK).body(personDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<Person> createPerson(@RequestBody Person person){
        System.out.println("controller : " + person.toString());
        personService.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(person);

    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> fullUpdatePerson(@RequestBody PersonDTO personDTO, @PathVariable("id") Long id) {
        try {
            PersonDTO updatedPerson = personService.updatePerson(id, personDTO);
            return ResponseEntity.ok(updatedPerson);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PersonDTO> partlyUpdatePerson(@PathVariable("id") Long id,@RequestBody PersonDTO personDTO){
        try {
            PersonDTO partlyUpdatePerson = personService.partlyUpdatePerson(id, personDTO);
            return ResponseEntity.ok(partlyUpdatePerson);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

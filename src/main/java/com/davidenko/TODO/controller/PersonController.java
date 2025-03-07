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
    public ResponseEntity<PersonDTO> getAllPersons(){
        List<PersonDTO> personList = personService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body((PersonDTO) personList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable("id") Long id){
       PersonDTO personDTO = personService.getPersonById(id);
        return ResponseEntity.status(HttpStatus.OK).body(personDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<Person> createPerson(@RequestBody Person person){
        personService.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(person);

    }

}

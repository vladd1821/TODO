package com.davidenko.TODO.service;

import com.davidenko.TODO.model.DTO.PersonDTO;
import com.davidenko.TODO.model.Person;
import com.davidenko.TODO.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;

    public PersonService(PersonRepository personRepository,ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
    }

    public List<PersonDTO> getAll() {
       List<Person> list =  personRepository.findAll();
       return list.stream()
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());

    }


    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO,Person.class);
    }
    private PersonDTO convertToDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }

    public PersonDTO getPersonById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent())
            return convertToDTO(person.get());
        else throw new EntityNotFoundException("Person not found with id: " + id);
    }

    public Person save(Person person) {
        return personRepository.save(person);

    }
}

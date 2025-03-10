package com.davidenko.TODO.service;

import com.davidenko.TODO.model.DTO.PersonDTO;
import com.davidenko.TODO.model.Person;
import com.davidenko.TODO.model.Task;
import com.davidenko.TODO.repository.PersonRepository;
import com.davidenko.TODO.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PersonService {
    private final PersonRepository personRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PersonService(PersonRepository personRepository,TaskRepository taskRepository, ModelMapper modelMapper) {
        this.personRepository = personRepository;
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }

    public List<PersonDTO> getAll() {
       List<Person> list =  personRepository.findAll();
       return list.stream()
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());
    }

    public PersonDTO getPersonById(Long id) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent())
            return convertToDTO(person.get());
        else throw new EntityNotFoundException("Person not found with id: " + id);
    }

    public Person save(Person person) {
        personRepository.save(person);
        return person;

    }

    public PersonDTO updatePerson(Long id, PersonDTO personDTO) throws Exception {
        Person person = personRepository.findById(id).orElseThrow(()->new Exception("person not found"));
        person.setName(personDTO.getName());
        person.setDateBirth(personDTO.getDateBirth());
        person.setLastName(personDTO.getLastName());

        personRepository.save(person);
        return convertToDTO(person);
    }


    public PersonDTO partlyUpdatePerson(Long id,PersonDTO personDTO) throws Exception {
        Person person = personRepository.findById(id).orElseThrow(()->new Exception("person not found"));

        if (personDTO.getName() != null){
            person.setName(personDTO.getName());}
        if (personDTO.getDateBirth()!= null){
            person.setDateBirth(personDTO.getDateBirth());}
        if (personDTO.getLastName() !=null){
            person.setLastName(personDTO.getLastName());}

        personRepository.save(person);
        return convertToDTO(person);
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO,Person.class);
    }
    private PersonDTO convertToDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }

    public List<Task> getAllTasksByPersonId(Long id) {
        return taskRepository.findAllByPersonId(id);
    }
}

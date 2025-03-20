package com.davidenko.TODO.controller;

import com.davidenko.TODO.model.DTO.PersonDTO;
import com.davidenko.TODO.model.DTO.TaskDTO;
import com.davidenko.TODO.model.Person;
import com.davidenko.TODO.model.Task;
import com.davidenko.TODO.service.PersonService;
import com.davidenko.TODO.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@Slf4j
public class PersonController {
    private final PersonService personService;
    private final TaskService taskService;

    public PersonController(PersonService personService,TaskService taskService) {
        this.personService = personService;
        this.taskService = taskService;
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
//        log.info("controller : " + person.toString());
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

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> getTasksByPersonId(@PathVariable("id") Long id){
        List<Task> taskList = personService.getAllTasksByPersonId(id);
        return ResponseEntity.status(HttpStatus.OK).body(taskList);
    }
    @GetMapping("/{personId}/pagi_sort")
    public ResponseEntity<Page<TaskDTO>> getTasksByPerson(
            @PathVariable Long personId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "startedAt") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Page<TaskDTO> tasks = personService.getTasksByPersonId(personId, page, size, sortBy, direction);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/{id}/create")
    public ResponseEntity<TaskDTO> createTaskByPersonId(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        personService.createTaskToPersonById(id, taskDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(taskDTO);

    }

    @PatchMapping("/task/{id}/editTask")
    public ResponseEntity<TaskDTO> partlyUpdateTaskByTaskId(@PathVariable Long id, @RequestBody TaskDTO taskDTO){
        TaskDTO updatedTask = taskService.partlyUpdateTaskByTaskId(id, taskDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }



}

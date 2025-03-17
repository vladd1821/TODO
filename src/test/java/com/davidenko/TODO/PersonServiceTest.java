package com.davidenko.TODO;

import com.davidenko.TODO.model.DTO.PersonDTO;
import com.davidenko.TODO.model.DTO.Status;
import com.davidenko.TODO.model.DTO.TaskDTO;
import com.davidenko.TODO.model.Person;
import com.davidenko.TODO.model.Task;
import com.davidenko.TODO.repository.PersonRepository;
import com.davidenko.TODO.repository.TaskRepository;
import com.davidenko.TODO.service.PersonService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @InjectMocks
    private PersonService personService;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ModelMapper modelMapper;

    private Person person;
    private PersonDTO personDTO;
    private Task task;
    private TaskDTO taskDTO;

    @BeforeEach
    public void setUp() {
        person = new Person();
        person.setId(1L);
        person.setName("John");
        person.setLastName("Doe");
        person.setDateBirth(LocalDate.of(1990, 1, 1));
        person.setTasks(new ArrayList<>());

        personDTO = new PersonDTO();
        personDTO.setName("John");
        personDTO.setLastName("Doe");
        personDTO.setDateBirth(LocalDate.of(1990, 1, 1));

        task = new Task();
        task.setId(1L);
        task.setTaskName("Test Task");
        task.setDescription("Task description");
        task.setStatus(Status.IN_PROGRESS);
        task.setStartedAt(LocalDateTime.now());
        task.setExpiredAt(LocalDateTime.now().plusDays(2));
        task.setPerson(person);

        taskDTO = new TaskDTO();
        taskDTO.setTaskName("Test Task");
        taskDTO.setDescription("Task description");
        taskDTO.setStatus(Status.IN_PROGRESS);
        taskDTO.setStartedAt(LocalDateTime.now());
        taskDTO.setExpiredAt(LocalDateTime.now().plusDays(2));
    }

    @Test
    void getAll_ShouldReturnListOfPersonDTOs() {
        when(personRepository.findAll()).thenReturn(List.of(person));
        when(modelMapper.map(person, PersonDTO.class)).thenReturn(personDTO);

        List<PersonDTO> result = personService.getAll();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getName());
        verify(personRepository, times(1)).findAll();
    }

    @Test
    void getPersonById_ShouldReturnPersonDTO_WhenPersonExists() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(modelMapper.map(person, PersonDTO.class)).thenReturn(personDTO);

        PersonDTO result = personService.getPersonById(1L);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void getPersonById_ShouldThrowException_WhenPersonNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> personService.getPersonById(1L));
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void save_ShouldSaveAndReturnPerson() {
        when(personRepository.save(person)).thenReturn(person);

        Person result = personService.save(person);

        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void updatePerson_ShouldUpdateAndReturnPersonDTO_WhenPersonExists() throws Exception {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(modelMapper.map(any(Person.class), eq(PersonDTO.class)))
                .thenAnswer(invocation -> {
                    Person updatedPerson = invocation.getArgument(0);
                    PersonDTO personDTO1 = new PersonDTO();
                    personDTO1.setName(updatedPerson.getName());
                    personDTO1.setDateBirth(updatedPerson.getDateBirth());
                    personDTO1.setLastName(updatedPerson.getLastName());
                    return personDTO1;
                });

        PersonDTO updatedPersonDTO = new PersonDTO();
        updatedPersonDTO.setName("Updated");
        updatedPersonDTO.setLastName("Person");
        updatedPersonDTO.setDateBirth(LocalDate.of(2000, 5, 15));

        PersonDTO result = personService.updatePerson(1L, updatedPersonDTO);

        assertNotNull(result);
        assertEquals("Updated", result.getName());
        assertEquals("Person", result.getLastName());
        assertEquals(LocalDate.of(2000, 5, 15), result.getDateBirth());

        verify(personRepository, times(1)).save(any(Person.class));
    }

    @Test
    void updatePerson_ShouldThrowException_WhenPersonNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> personService.updatePerson(1L, personDTO));
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void getAllTasksByPersonId_ShouldReturnListOfTasks() {
        when(taskRepository.findAllByPersonId(1L)).thenReturn(List.of(task));

        List<Task> result = personService.getAllTasksByPersonId(1L);

        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTaskName());
        verify(taskRepository, times(1)).findAllByPersonId(1L);
    }

    @Test
    void createTaskToPersonById_ShouldCreateTask() {
        when(personRepository.findById(1L)).thenReturn(Optional.of(person));
        when(modelMapper.map(taskDTO, Task.class)).thenReturn(task);

        personService.createTaskToPersonById(1L, taskDTO);

        assertEquals(1, person.getTasks().size());
        verify(personRepository, times(1)).save(person);
    }

    @Test
    void createTaskToPersonById_ShouldThrowException_WhenPersonNotFound() {
        when(personRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> personService.createTaskToPersonById(1L, taskDTO));
        verify(personRepository, times(1)).findById(1L);
    }

    @Test
    void getTasksByPersonId_ShouldReturnPagedTasks() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("taskName").ascending());
        Page<Task> page = new PageImpl<>(List.of(task));

        when(taskRepository.findAllByPersonId(1L, pageable)).thenReturn(page);
        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);

        Page<TaskDTO> result = personService.getTasksByPersonId(1L, 0, 10, "taskName", "asc");

        assertEquals(1, result.getTotalElements());
        assertEquals("Test Task", result.getContent().get(0).getTaskName());
        verify(taskRepository, times(1)).findAllByPersonId(1L, pageable);
    }
}
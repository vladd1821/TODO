package com.davidenko.TODO.service;

import com.davidenko.TODO.model.DTO.PersonDTO;
import com.davidenko.TODO.model.DTO.Status;
import com.davidenko.TODO.model.DTO.TaskDTO;
import com.davidenko.TODO.model.Person;
import com.davidenko.TODO.model.Task;
import com.davidenko.TODO.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private TaskService taskService;

    private Task task;
    private TaskDTO taskDTO;
    private Person person;
    private PersonDTO personDTO;


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
    void getAllTasks_shouldReturnAllTasksDTO() {
        when(taskRepository.findAll()).thenReturn(List.of(task));
        when(modelMapper.map(task, TaskDTO.class)).thenReturn(taskDTO);

        List<TaskDTO> taskDTOList = taskService.getAllTasks();

        assertEquals(1, taskDTOList.size());
        assertEquals("Test Task", taskDTOList.get(0).getTaskName());
        verify(taskRepository, times(1)).findAll();

    }

    @Test
    void getAllTasksByStatus() {
    }

    @Test
    void deleteTaskById() {
    }

    @Test
    void save() {
    }

    @Test
    void partlyUpdateTaskByTaskId() {
    }
}
package com.davidenko.TODO.service;

import com.davidenko.TODO.model.DTO.PersonDTO;
import com.davidenko.TODO.model.DTO.Status;
import com.davidenko.TODO.model.DTO.TaskDTO;
import com.davidenko.TODO.model.Person;
import com.davidenko.TODO.model.Task;
import com.davidenko.TODO.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public TaskService(TaskRepository taskRepository, ModelMapper modelMapper) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
    }


    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(this::convertToTaskDTO).collect(Collectors.toList());
    }
    public List<TaskDTO> getAllTasksByStatus(Status status) {

        return taskRepository.findAllByStatus(status).stream().map(this::convertToTaskDTO).collect(Collectors.toList());
    }


    private Task convertToTask(TaskDTO taskDTO) {
        return modelMapper.map(taskDTO,Task.class);
    }
    private TaskDTO convertToTaskDTO(Task task){
        return modelMapper.map(task, TaskDTO.class);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }
}

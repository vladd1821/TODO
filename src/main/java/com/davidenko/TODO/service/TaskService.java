package com.davidenko.TODO.service;

import com.davidenko.TODO.model.DTO.Status;
import com.davidenko.TODO.model.DTO.TaskDTO;
import com.davidenko.TODO.model.Task;
import com.davidenko.TODO.repository.TaskRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        return modelMapper.map(taskDTO, Task.class);
    }


    private TaskDTO convertToTaskDTO(Task task) {
        return modelMapper.map(task, TaskDTO.class);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public void save(TaskDTO taskDTO) {
        taskRepository.save(convertToTask(taskDTO));

    }

    public TaskDTO partlyUpdateTaskByTaskId(Long id, TaskDTO taskDTO) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {

            Task updateTask = task.get();
            if (taskDTO.getTaskName() != null)
                updateTask.setTaskName(taskDTO.getTaskName());
            if (taskDTO.getDescription() != null)
                updateTask.setDescription(taskDTO.getDescription());
            if (taskDTO.getStatus() != null)
                updateTask.setStatus(taskDTO.getStatus());
            if (taskDTO.getExpiredAt() != null)
                updateTask.setExpiredAt(taskDTO.getExpiredAt());
            taskRepository.save(updateTask);
            return convertToTaskDTO(updateTask);
        } else throw new RuntimeException("task not found, id: " + id);

    }
}

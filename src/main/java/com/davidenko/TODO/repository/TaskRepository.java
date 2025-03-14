package com.davidenko.TODO.repository;

import com.davidenko.TODO.model.DTO.Status;
import com.davidenko.TODO.model.DTO.TaskDTO;
import com.davidenko.TODO.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findAllByPersonId(Long id);

    List<Task> findAllByStatus(Status status);
    Page<Task> findAllByPersonId(Long personId, Pageable pageable);

}

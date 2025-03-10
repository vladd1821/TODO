package com.davidenko.TODO.model;

import com.davidenko.TODO.model.DTO.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "taskname")
    private String taskName;
    @Column(name = "description")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @Column(name = "startedat")
    private LocalDateTime startedAt;
    @Column(name = "expiredat")
    private LocalDateTime expiredAt;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Каскадное удаление и загрузка по запросу
    @JsonIgnore
    @JoinColumn(name = "person_id")
    private Person person;

    public Task() {
    }

    public Task(Long id, String taskName, String description, Status status, LocalDateTime startedAt, LocalDateTime expiredAt, Person person) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.status = status;
        this.startedAt = startedAt;
        this.expiredAt = expiredAt;
        this.person = person;
    }

    public Long getId() {
        return id;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public Person getPerson() {
        return person;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public void setExpiredAt(LocalDateTime expiredAt) {
        this.expiredAt = expiredAt;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", taskName='" + taskName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

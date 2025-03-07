package com.davidenko.TODO.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String taskName;
    private String description;
    private Status status;
    private LocalDateTime startedAt;
    private LocalDateTime expiredAt;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Каскадное удаление и загрузка по запросу
    @JoinColumn(name = "person_id")
    private Person person;




}

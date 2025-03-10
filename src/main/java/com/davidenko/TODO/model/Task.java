package com.davidenko.TODO.model;

import com.davidenko.TODO.model.DTO.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "task")
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Каскадное удаление и загрузка по запросу
    @JoinColumn(name = "person_id")
    private Person person;




}

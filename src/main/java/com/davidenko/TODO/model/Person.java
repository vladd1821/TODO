package com.davidenko.TODO.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "datebirth")
    private LocalDate dateBirth;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Каскадное удаление и загрузка по запросу
    @JsonIgnore
    private List<Task> tasks;


    public Person() {
    }

    public Person(Long id, String name, String lastName, LocalDate dateBirth, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Person(String name, String lastName, LocalDate dateBirth) {
        this.name = name;
        this.lastName = lastName;
        this.dateBirth = dateBirth;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateBirth=" + dateBirth +
                ", tasks=" + tasks +
                '}';
    }
}

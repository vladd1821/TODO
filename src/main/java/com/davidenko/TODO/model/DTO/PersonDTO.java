package com.davidenko.TODO.model.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
public class PersonDTO {
    private String name;
    private String lastName;
    private LocalDate dateBirth;
}

package ru.custis.course_selection.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {

    private long id;
    private String firstName;
    private String secondName;
}

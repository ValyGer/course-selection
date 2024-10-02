package ru.custis.course_selection.entity;

import jakarta.persistence.Entity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {

    private long id;
    private String title;
    private long limit;
    private List<Student> students;
}

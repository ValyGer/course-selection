package ru.custis.course_selection.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title", unique = true)
    private String title;
    @Column(name = "limit_person")
    private long limitPerson;

    @Column(name = "start_reg")
    private ZonedDateTime startReg;

    @Column(name = "finish_reg")
    private ZonedDateTime finishReg;

    @ManyToMany
    @JoinTable(name = "registrations", joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"))
    private List<Student> students;
}

package ru.custis.course_selection.dto.student;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private String firstname;
    private String lastname;
    private List<String> coursesName;
}

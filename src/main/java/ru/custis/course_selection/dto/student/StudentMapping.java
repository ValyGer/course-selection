package ru.custis.course_selection.dto.student;

import org.springframework.stereotype.Component;
import ru.custis.course_selection.entity.Student;

@Component
public interface StudentMapping {

    StudentDto studentToStudentDto(Student student);

    Student studentInitDtoToStudent(StudentInitDto studentInitDto);

}

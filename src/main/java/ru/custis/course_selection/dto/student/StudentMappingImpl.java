package ru.custis.course_selection.dto.student;

import org.springframework.stereotype.Component;
import ru.custis.course_selection.entity.Course;
import ru.custis.course_selection.entity.Student;

import java.util.stream.Collectors;

@Component
public class StudentMappingImpl implements StudentMapping {

    @Override
    public StudentDto studentToStudentDto(Student student) {
        StudentDto studentDto = new StudentDto();
        studentDto.setFirstname(student.getFirstname());
        studentDto.setLastname(student.getLastname());
        studentDto.setCoursesName(student.getCourses().stream().map(Course::getTitle).collect(Collectors.toList()));
        return studentDto;
    }

    @Override
    public Student studentInitDtoToStudent(StudentInitDto studentInitDto) {
        Student student = new Student();
        student.setFirstname(studentInitDto.getFirstname());
        student.setLastname(studentInitDto.getLastname());
        return student;
    }
}
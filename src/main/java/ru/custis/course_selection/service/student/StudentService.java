package ru.custis.course_selection.service.student;

import ru.custis.course_selection.dto.student.StudentDto;
import ru.custis.course_selection.dto.student.StudentInitDto;

import java.util.List;

public interface StudentService {

    List<StudentDto> getAllStudents();

    StudentDto getStudentById(Long studentId);

    StudentDto createStudent(StudentInitDto studentInitDto);

    StudentDto updateStudent(Long studentId, StudentInitDto studentInitDto);

    void deleteStudent(Long studentId);

    StudentDto registrationOnCourse(Long studentId, Long courseId);

    StudentDto leaveCourse(Long studentId, Long courseId);
}

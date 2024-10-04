package ru.custis.course_selection.service.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.custis.course_selection.dto.course.CourseInitDto;
import ru.custis.course_selection.dto.student.StudentInitDto;
import ru.custis.course_selection.exception.DataConflictRequest;
import ru.custis.course_selection.service.course.CourseService;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class StudentRegistrationTestIT {

    @Autowired
    CourseService courseService;

    @Autowired
    StudentServiceImpl studentService;

    @Test
    void registrationOnCourse() {
        studentService.createStudent(new StudentInitDto("Иван", "Иванов"));
        studentService.createStudent(new StudentInitDto("Петр", "Петров"));
        studentService.createStudent(new StudentInitDto("Артем", "Артемов"));

        courseService.createCourse(new CourseInitDto("информатика", 2L));

        assertThrows(DataConflictRequest.class, () -> {
            for (long studentId = 1L; studentId <= 3L; studentId++) {
                studentService.registrationOnCourse(studentId, 1L);
            }
        });
    }
}
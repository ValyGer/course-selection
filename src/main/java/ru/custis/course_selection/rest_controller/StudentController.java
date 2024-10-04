package ru.custis.course_selection.rest_controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.custis.course_selection.dto.student.StudentDto;
import ru.custis.course_selection.dto.student.StudentInitDto;
import ru.custis.course_selection.service.student.StudentService;
import ru.custis.course_selection.service.time_window.TimeWindowService;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    public final StudentService studentService;

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAllStudents());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable("studentId") Long studentId) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentById(studentId));
    }

    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@Valid @RequestBody StudentInitDto studentInitDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentService.createStudent(studentInitDto));
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable("studentId") Long studentId,
                                                    @Valid @RequestBody StudentInitDto studentInitDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.updateStudent(studentId, studentInitDto));
    }

    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }

    @PatchMapping("/{studentId}/add/{courseId}")
    public ResponseEntity<StudentDto> addToCourse(@PathVariable("studentId") Long studentId,
                                                  @PathVariable("courseId") Long courseId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.registrationOnCourse(studentId, courseId));
    }

    @PatchMapping("/{studentId}/leave/{courseId}")
    public ResponseEntity<StudentDto> leaveCourse(@PathVariable("studentId") Long studentId,
                                                  @PathVariable("courseId") Long courseId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentService.leaveCourse(studentId, courseId));
    }
}

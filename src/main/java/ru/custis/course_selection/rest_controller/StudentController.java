package ru.custis.course_selection.rest_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
//    @GetMapping("/ined")
//    public void getAllStudent() {
//        List<Student> students = studentRepository.findAll();
//        for(Student student: students){
//            System.out.println(student.getFirstname() + student.getCourses());
//        }
//    }
}

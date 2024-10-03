package ru.custis.course_selection.rest_controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.custis.course_selection.dto.CourseDto;
import ru.custis.course_selection.dto.CourseInitDto;
import ru.custis.course_selection.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/courses")
@Validated
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourse() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAllCourse());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getAllCourse(@PathVariable("courseId") Long courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseById(courseId));
    }

    @PostMapping
    public ResponseEntity<CourseDto> createCourse(@Valid @RequestBody CourseInitDto courseIniDto) {
        System.out.println(courseIniDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseService.createCourse(courseIniDto));
    }

    @PatchMapping("/{courseId}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable("courseId") Long courseId,
                                                @Valid @RequestBody CourseInitDto courseIniDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.updateCourse(courseId, courseIniDto));
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable("courseId") Long courseId) {
        courseService.deleteCourse(courseId);
    }
}

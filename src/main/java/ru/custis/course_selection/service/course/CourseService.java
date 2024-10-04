package ru.custis.course_selection.service.course;

import ru.custis.course_selection.dto.course.CourseDto;
import ru.custis.course_selection.dto.course.CourseInitDto;
import ru.custis.course_selection.entity.Course;

import java.util.List;

public interface CourseService {
    List<CourseDto> getAllCourse();

    CourseDto getCourseById(Long courseId);

    Course getCourseByIdForStudent(Long courseId);

    CourseDto createCourse(CourseInitDto courseIniDto);

    CourseDto updateCourse(Long courseId, CourseInitDto courseIniDto);

    void deleteCourse(Long courseId);
}

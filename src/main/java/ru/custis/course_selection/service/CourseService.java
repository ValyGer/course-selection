package ru.custis.course_selection.service;

import ru.custis.course_selection.dto.CourseDto;
import ru.custis.course_selection.dto.CourseInitDto;

import java.util.List;

public interface CourseService {
    List<CourseDto> getAllCourse();

    CourseDto getCourse(Long courseId);

    CourseDto createCourse(CourseInitDto courseIniDto);

    CourseDto updateCourse(Long courseId, CourseInitDto courseIniDto);

    void deleteCourse(Long courseId);
}

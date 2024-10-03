package ru.custis.course_selection.dto;

import org.springframework.stereotype.Component;
import ru.custis.course_selection.entity.Course;

import java.util.List;

@Component
public class CourseMapping {

    public CourseDto courseToCourseDto(Course course) {
        CourseDto courseDto = new CourseDto();
        courseDto.setTitle(course.getTitle());
        courseDto.setNumberOccupiedPlaces(course.getLimitPerson() - course.getStudents().size());
        courseDto.setNumberAvailablePlaces(course.getStudents().size());
        List<String> studentNames = course.getStudents().stream()
                .map(student -> student.getFirstname() + " " + student.getLastname()).toList();
        courseDto.setStudentNames(studentNames);

        return courseDto;
    }

    public Course courseInitDtoToCourse(CourseInitDto courseInitDto) {
        Course course = new Course();
        course.setTitle(courseInitDto.getTitle());
        course.setLimitPerson(courseInitDto.getLimitPerson());
        return course;
    }
}
